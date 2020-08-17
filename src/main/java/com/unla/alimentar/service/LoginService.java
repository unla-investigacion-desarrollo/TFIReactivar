package com.unla.alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.Login;
import com.unla.alimentar.repository.LoginRepository;

@Service
public class LoginService {

	@Autowired
	private LoginRepository repository;
	
	public Login traerLoginPorId(Long id) {
		return repository.findByIdLogin(id);
	}
	
}
