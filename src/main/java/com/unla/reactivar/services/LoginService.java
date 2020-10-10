package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.IncorrectToken;
import com.unla.reactivar.exceptions.IncorrectUserOrPassword;
import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.exceptions.UserIsAlreadyInactive;
import com.unla.reactivar.models.Login;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.repositories.LoginRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.LoginPostResVo;
import com.unla.reactivar.vo.LoginVo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional(readOnly = true)
public class LoginService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	private static final long ACTIVO = 2;
	
	@Autowired
	private LoginRepository repository;

	@Value("${token_auth.key}")
	private String secretKey;

	@Autowired
	private PersonaService personaService;

	@Transactional
	public LoginPostResVo realizarLogin(LoginVo loginVo) {
		String email = loginVo.getEmail();
		Login login = repository.findByEmail(email);

		String passwordHash = DigestUtils.sha256Hex(loginVo.getClave());

		if (login == null || !login.getClave().equals(passwordHash)) {
			throw new IncorrectUserOrPassword();
		}

		Persona persona = personaService.traerPersonaPorEmail(email);

		if (persona.getEstadoPersona().getIdEstadoPersona() != ACTIVO) {
			throw new UserIsAlreadyInactive();
		}

		if(login.getToken() == null){
			login.setToken(getJWTToken(login.getEmail()));
		}
		
		repository.save(login);
		
		LoginPostResVo loginResp = new LoginPostResVo();
		loginResp.setToken(login.getToken());
		loginResp.setIdPersona(persona.getIdPersona());
		loginResp.setIdPerfil(persona.getPerfil().getIdPerfil());
		log.info("Se logea [{}]", email);

		return loginResp;
	}
	
	public LoginPostResVo realizarLoginToken(String token) {
		Login login = repository.findByToken(token);

		if (login == null) {
			throw new IncorrectToken();
		}

		Persona persona = personaService.traerPersonaPorEmail(login.getEmail());

		if (persona.getEstadoPersona().getIdEstadoPersona() != ACTIVO) {
			throw new UserIsAlreadyInactive();
		}
		
		LoginPostResVo loginResp = new LoginPostResVo();
		loginResp.setToken(login.getToken());
		loginResp.setIdPersona(persona.getIdPersona());
		loginResp.setIdPerfil(persona.getPerfil().getIdPerfil());

		log.info("Se logea [{}]", login.getEmail());

		return loginResp;
	}

	public List<Login> traerTodosLogins() {
		log.info("Se traeran todos los login");
		return repository.findAll();
	}

	@Transactional
	public void borrarLogin(String email) {
		Login login = repository.findByEmail(email);

		if (login == null) {
			throw new ObjectNotFound("Login");
		}
		log.info("Se traeran todos los login");

		repository.delete(login);
	}

	@Transactional
	public Login actualizarLogin(LoginVo loginVo) {
		Login login = repository.findByEmail(loginVo.getEmail());

		if (login == null) {
			throw new ObjectNotFound("Login");
		}

		login.setClave(DigestUtils.sha256Hex(loginVo.getClave()));
		login.setEmail(loginVo.getEmail());

		try {
			login = repository.saveAndFlush(login);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		login.setToken(null);
		login.setClave(null);
		log.info("Se actulizara login [{}]", login.getEmail());

		return login;
	}

	@Transactional
	public Login crearLogin(LoginVo loginVo) {
		Login login = new Login();

		login.setClave(DigestUtils.sha256Hex(loginVo.getClave()));
		login.setEmail(loginVo.getEmail());

		try {
			log.info("Se creara login [{}]", login.getEmail());
			login = repository.save(login);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return login;
	}

	private String getJWTToken(String username) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(DateUtils.fechaHoy())
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
		return token;
	}
	
}
