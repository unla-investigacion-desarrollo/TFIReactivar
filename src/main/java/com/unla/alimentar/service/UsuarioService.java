package com.unla.alimentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.Usuario;
import com.unla.alimentar.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario traerUsuarioPorUsuario(String usuario) {
		return usuarioRepository.findByUsuario(usuario);
	}

	public List<Usuario> traerTodos() {
		return usuarioRepository.findAll();
	}
		
}
