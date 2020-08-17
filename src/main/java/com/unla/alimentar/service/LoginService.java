package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.Login;
import com.unla.alimentar.repository.LoginRepository;

@Service
@Transactional(readOnly = true)
public class LoginService {

	@Autowired
	private LoginRepository repository;

	public Login traerLoginPorId(Long id) {
		return repository.findByIdLogin(id);
	}

	public List<Login> traerTodos(Long id) {
		return repository.findAll();
	}

	@Transactional
	public void borrarLogin(long id) {
		Login login = repository.findByIdLogin(id);

		if (login == null) {
			throw new ObjectNotFound("Login");
		}

		repository.delete(login);
	}

}
