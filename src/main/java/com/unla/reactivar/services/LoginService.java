package com.unla.reactivar.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.IncorrectUserOrPassword;
import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Login;
import com.unla.reactivar.repositories.LoginRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.LoginVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional(readOnly = true)
public class LoginService {

	@Autowired
	private LoginRepository repository;

	@Value("${token_auth.duration}")
	private long timeToExpire;

	@Value("${token_auth.key}")
	private String secretKey;

	public Login realizarLogin(LoginVo loginVo) {
		Login login = repository.findByEmail(loginVo.getEmail());

		String passwordHash = DigestUtils.sha256Hex(loginVo.getClave());

		if (login == null || !login.getClave().equals(passwordHash)) {
			throw new IncorrectUserOrPassword();
		}

		login.setToken(getJWTToken(login.getEmail()));
		login.setClave(null);

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

		try {
			login = repository.save(login);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return login;
	}

	@Transactional
	public Login crearLogin(LoginVo loginVo) {
		Login login = new Login();

		login.setClave(DigestUtils.sha256Hex(loginVo.getClave()));
		login.setEmail(loginVo.getEmail());

		try {
			login = repository.save(login);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return login;
	}

	private String getJWTToken(String username) {
		Claims claims = Jwts.claims().setSubject(username);

		String token = Jwts.builder().setSubject(username).setClaims(claims).setIssuedAt(DateUtils.fechaHoy())
				.setExpiration(new Date(DateUtils.fechaHoy().getTime() + timeToExpire))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return token;
	}
}
