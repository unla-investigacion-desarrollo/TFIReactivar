package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Login;
import com.unla.alimentar.repositories.LoginRepository;
import com.unla.alimentar.vo.LoginVo;

@Service
@Transactional(readOnly = true)
public class LoginService {

	@Autowired
	private LoginRepository repository;

	public Login traerLoginPorId(Long id) {
		return repository.findByIdLogin(id);
	}

	public List<Login> traerTodos() {
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

	public Login actualizarLogin(Long id, LoginVo loginVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Login crearLogin(LoginVo loginVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
