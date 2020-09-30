package com.unla.reactivar.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.IncorrectTokenOrTokenExpiredPwd;
import com.unla.reactivar.models.ResetAndValidatingToken;
import com.unla.reactivar.repositories.ResetAndValidatingTokenRepository;
import com.unla.reactivar.utils.DateUtils;

@Service
@Transactional(readOnly = false)
public class ResetAndValidatingTokenService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ResetAndValidatingTokenRepository repository;

	@Transactional
	public ResetAndValidatingToken crearResetOrValidateToken(ResetAndValidatingToken pwd) {
		log.info("Se creara token para resetear pwd o validar usuario");
		return repository.save(pwd);
	}

	public ResetAndValidatingToken validateResetOrValidatingToken(String token) {
		final ResetAndValidatingToken passToken = repository.findByToken(token);
		log.info("Se validara el token [{}]", token);

		if (passToken == null || isTokenExpired(passToken)) {
			throw new IncorrectTokenOrTokenExpiredPwd();
		}
		
		return passToken;

	}

	private boolean isTokenExpired(ResetAndValidatingToken passToken) {
		return passToken.getExpiryDate().before(DateUtils.fechaHoy());
	}

}
