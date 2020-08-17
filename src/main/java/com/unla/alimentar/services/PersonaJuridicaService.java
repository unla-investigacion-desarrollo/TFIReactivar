package com.unla.alimentar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Login;
import com.unla.alimentar.models.Perfil;
import com.unla.alimentar.models.Persona;
import com.unla.alimentar.models.PersonaJuridica;
import com.unla.alimentar.repositories.PersonaRepository;
import com.unla.alimentar.vo.PersonaJuridicaVo;

@Service
@Transactional(readOnly = true)
public class PersonaJuridicaService {
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Transactional
	public Persona crearPersona(PersonaJuridicaVo personaVo) {
		
		PersonaJuridica persona = new PersonaJuridica();
		persona.setCelular(personaVo.getCelular());
		persona.setCuit(personaVo.getCuit());
		persona.setRazonSocial(personaVo.getRazonSocial());
		Login login = loginService.traerLoginPorId(personaVo.getIdLogin());
		Perfil perfil = perfilService.traerPerfilPorId(personaVo.getIdPerfil());
		
		if(login == null || perfil == null) {
			throw new ObjectNotFound("Login o perfil");
		}
		
		persona.setLogin(login);
		persona.setPerfil(perfil);
		return personaRepository.save(persona);
	}

	public Persona traerPersonaPorId(long idPersona) {
		return personaRepository.findByIdPersona(idPersona);
	}

	public void borrarPersona(long id) {
		Persona persona = personaRepository.findByIdPersona(id);
		
		if(persona == null) {
			throw new ObjectNotFound("Persona");
		}
		
		personaRepository.delete(persona);
	}

}
