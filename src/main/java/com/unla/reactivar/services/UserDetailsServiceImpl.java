package com.unla.reactivar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.unla.reactivar.models.MyUserDetails;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.repositories.PersonaRepository;

public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private PersonaRepository repository;

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

		Persona persona = repository.findByEmail(mail);

		if (persona == null) {
			throw new UsernameNotFoundException("No se pudo encontrar usuario");
		}
		return new MyUserDetails(persona);

	}

}
