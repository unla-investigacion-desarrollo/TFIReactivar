package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Login;
import com.unla.alimentar.models.Perfil;
import com.unla.alimentar.models.Persona;
import com.unla.alimentar.models.PersonaFisica;
import com.unla.alimentar.models.Ubicacion;
import com.unla.alimentar.repositories.PersonaFisicaRepository;
import com.unla.alimentar.utils.DateUtils;
import com.unla.alimentar.vo.PersonaFisicaVo;

@Service
@Transactional(readOnly = true)
public class PersonaFisicaService {

	@Autowired
	private PersonaFisicaRepository repository;

	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private UbicacionService ubicacionService;
	
	@Autowired
	private LoginService loginService;
	
	public Persona traerPersonaFisicaPorId(Long id) {
		return repository.findByIdPersona(id);
	}

	public List<PersonaFisica> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarPersonaFisica(long id) {
		PersonaFisica registro = repository.findByIdPersona(id);

		if (registro == null) {
			throw new ObjectNotFound("PersonaFisica");
		}

		repository.delete(registro);
	}

	@Transactional
	public Persona crearPersonaFisica(PersonaFisicaVo personaFisicaVo) {
		PersonaFisica persona = new PersonaFisica();

		adaptVoToPersonaFisica(persona, personaFisicaVo);
		
		Ubicacion ubicacion = ubicacionService.crearUbicacion(personaFisicaVo.getUbicacionVo());
		Login login = loginService.crearLogin(personaFisicaVo.getLoginVo());
		persona.setUbicacion(ubicacion);
		persona.setLogin(login);
		
		return repository.save(persona);
	}

	@Transactional
	public Persona actualizarPersonaFisica(Long id, PersonaFisicaVo personaFisicaVo) {
		PersonaFisica persona = repository.findByIdPersona(id);
		
		if(persona == null) {
			throw new ObjectNotFound("Persona");
		}
		
		adaptVoToPersonaFisica(persona, personaFisicaVo);
		
		return repository.save(persona);
	}
	
	private void adaptVoToPersonaFisica(PersonaFisica persona, PersonaFisicaVo personaFisicaVo) {
		Perfil perfil = perfilService.traerPerfilPorId(personaFisicaVo.getIdPerfil());
		
		if(perfil == null) {
			throw new ObjectNotFound("Perfil");
		}
		
		persona.setNombre(personaFisicaVo.getApellido());
		persona.setApellido(personaFisicaVo.getApellido());
		persona.setCuil(personaFisicaVo.getCuil());
		persona.setCelular(personaFisicaVo.getCelular());
		persona.setUsuarioModi(personaFisicaVo.getUsuarioModi());
		persona.setFechaModi(DateUtils.fechaHoy());
		persona.setPerfil(perfil);
	}

}
