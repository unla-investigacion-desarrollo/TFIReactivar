package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Login;
import com.unla.reactivar.models.Perfil;
import com.unla.reactivar.models.PersonaJuridica;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.PersonaJuridicaRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.PersonaJuridicaVo;

@Service
@Transactional(readOnly = true)
public class PersonaJuridicaService {
	
	@Autowired
	private PersonaJuridicaRepository personaRepository;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private UbicacionService ubicacionService;
	
	@Autowired
	private LoginService loginService;
	
	@Transactional
	public PersonaJuridica crearPersona(PersonaJuridicaVo personaVo) {
		
		PersonaJuridica persona = new PersonaJuridica();
		
		adaptVoToPersonaJuridica(persona, personaVo);
		
		Ubicacion ubicacion = ubicacionService.crearUbicacion(personaVo.getUbicacionVo());

		persona.setUbicacion(ubicacion);
		
		Login login = loginService.crearLogin(personaVo.getLoginVo());
		
		persona.setLogin(login);
		
		try {
			persona = personaRepository.save(persona);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return persona;
	}

	public PersonaJuridica traerPersonaPorId(long idPersona) {
		return personaRepository.findByIdPersona(idPersona);
	}
	
	public List<PersonaJuridica> traerTodasPersonasJuridicas(){
		return personaRepository.findAll();
	}

	@Transactional
	public void borrarPersona(long id) {
		PersonaJuridica persona = personaRepository.findByIdPersona(id);
		
		if(persona == null) {
			throw new ObjectNotFound("Persona");
		}
		
		personaRepository.deletePersona(id);
	}

	@Transactional
	public PersonaJuridica actualizarPersonaJuridica(Long id, PersonaJuridicaVo personaVo) {
		PersonaJuridica persona = personaRepository.findByIdPersona(id);
		
		if(persona == null) {
			throw new ObjectNotFound("Persona");
		}
		
		adaptVoToPersonaJuridica(persona, personaVo);

		try {
			persona = personaRepository.save(persona);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return persona;
	}
	
	private void adaptVoToPersonaJuridica(PersonaJuridica persona, PersonaJuridicaVo personaVo) {
		persona.setCelular(personaVo.getCelular());
		persona.setCuit(personaVo.getCuit());
		persona.setRazonSocial(personaVo.getRazonSocial());
		persona.setFechaModi(DateUtils.fechaHoy());
		persona.setUsuarioModi(personaVo.getUsuarioModi());
		Perfil perfil = perfilService.traerPerfilPorId(personaVo.getIdPerfil());
		
		if(perfil == null) {
			throw new ObjectNotFound("Perfil");
		}
		
		persona.setPerfil(perfil);
	}

}
