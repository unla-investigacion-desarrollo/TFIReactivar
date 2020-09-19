package com.unla.reactivar.services;

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

	@Autowired
	private ResetAndValidatingTokenRepository repository;

	@Transactional
	public ResetAndValidatingToken crearResetOrValidateToken(ResetAndValidatingToken pwd) {
		
		return repository.save(pwd);
	}
	
	public ResetAndValidatingToken validateResetOrValidatingToken(String token) {
	    final ResetAndValidatingToken passToken = repository.findByToken(token);
	    
	    if(passToken == null || isTokenExpired(passToken)) {
	    	throw new IncorrectTokenOrTokenExpiredPwd();
	    }
	    
	    return passToken;
	    
	}
	 
	private boolean isTokenExpired(ResetAndValidatingToken passToken) {
	    return passToken.getExpiryDate().before(DateUtils.fechaHoy());
	}



}
