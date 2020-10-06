package com.unla.reactivar.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.unla.reactivar.services.FuncionService;

import lombok.Data;

@Data
public class MyUserDetails implements UserDetails {

	@Autowired
	private FuncionService service;

	private Persona persona;

	public MyUserDetails(Persona persona) {
		this.persona = persona;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<FuncionPerfil> funcionPerfil = persona.getPerfil().getFuncionesPerfil();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (int i = 0; i <= funcionPerfil.size(); i++) {
			Funcion funcion = service.traerFuncionPorId(funcionPerfil.get(i).getFuncion().getIdFuncion());
			authorities.add(new SimpleGrantedAuthority(funcion.getDescripcion()));
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return persona.getLogin().getClave();
	}

	@Override
	public String getUsername() {
		return persona.getLogin().getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
