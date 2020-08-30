package com.unla.reactivar.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.IncorrectUserOrPassword;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Login;
import com.unla.reactivar.repositories.LoginRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.LoginVo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional(readOnly = true)
public class LoginService {

	@Autowired
	private LoginRepository repository;
	
	private long timeToExpire = 200000;
	
	public Login realizarLogin(LoginVo loginVo) {
		Login login = repository.findByEmailAndPwd(loginVo.getEmail(), loginVo.getClave());

		if (login == null) {
			throw new IncorrectUserOrPassword();
		}

		login.setToken(getJWTToken(login.getEmail()));

		repository.save(login);

		return login;
	}

	public List<Login> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarLogin(String email) {
		Login login = repository.findByEmail(email);

		if (login == null) {
			throw new ObjectNotFound("Login");
		}

		repository.delete(login);
	}

	@Transactional
	public Login actualizarLogin(String email, LoginVo loginVo) {
		Login login = repository.findByEmail(email);

		if (login == null) {
			throw new ObjectNotFound("Login");
		}

		login.setClave(loginVo.getClave());
		login.setEmail(loginVo.getEmail());

		return repository.save(login);
	}

	@Transactional
	public Login crearLogin(LoginVo loginVo) {
		Login login = new Login();

		login.setClave(loginVo.getClave());
		login.setEmail(loginVo.getEmail());

		return repository.save(login);
	}

	private String getJWTToken(String username) {
		String secretKey = "q4t6w9z$C&F)J@NcRfUjXn2r5u8x!A%D";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("reactivar").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(DateUtils.fechaHoy()).setExpiration(new Date(DateUtils.fechaHoy().getTime() + timeToExpire))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return token;
	}
}
