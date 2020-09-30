package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.EstadoPersona;
import com.unla.reactivar.models.Login;
import com.unla.reactivar.models.Perfil;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.PersonaJuridica;
import com.unla.reactivar.models.ResetAndValidatingToken;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.PersonaJuridicaRepository;
import com.unla.reactivar.utils.CuilValidator;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.PersonaJuridicaVo;
import com.unla.reactivar.vo.ReqPutPersonaJuridicaVo;

@Service
@Transactional(readOnly = true)
public class PersonaJuridicaService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	private static final long INACTIVO = 1;
	private static final long ACTIVO = 2;

	@Value("${recovery.password.token.duration}")
	private int expiration;

	@Autowired
	private PersonaJuridicaRepository personaRepository;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private UbicacionService ubicacionService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private EstadoPersonaService estadoPersonaService;

	@Autowired
	private ResetAndValidatingTokenService pwdService;


	@Transactional
	public PersonaJuridica crearPersona(PersonaJuridicaVo personaVo) {

		PersonaJuridica persona = new PersonaJuridica();

		adaptVoToPersonaJuridica(persona, personaVo);

		try {
			log.info("Se creara persona juridica [{}]", persona.getCuit());
			persona = personaRepository.save(persona);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return persona;
	}

	public PersonaJuridica traerPersonaPorId(long idPersona) {
		log.info("Se traeran la personas juridicas por id");
		return personaRepository.findByIdPersona(idPersona);
	}
	
	public List<PersonaJuridica> traerTodosInactivos() {
		log.info("Se traeran todas las personas juridicas inactivas");
		return personaRepository.findAllInactivos();
	}

	public List<PersonaJuridica> traerTodos() {
		log.info("Se traeran todas las personas juridicas");
		return personaRepository.findAll();
	}

	@Transactional
	public void borrarPersona(long id) {
		PersonaJuridica persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}
		log.info("Se eliminara persona juridica [{}]", persona.getCuit());

		personaRepository.deletePersona(id);
	}

	@Transactional
	public PersonaJuridica actualizarPersonaJuridica(Long id, ReqPutPersonaJuridicaVo personaJuridicaVo) {
		PersonaJuridica persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		adaptPutVoToPersonaJuridica(persona, personaJuridicaVo);

		try {
			log.info("Se actualizara persona juridica [{}]", persona.getCuit());
			persona = personaRepository.save(persona);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return persona;
	}

	private void adaptVoToPersonaJuridica(PersonaJuridica persona, PersonaJuridicaVo personaVo) {
		Ubicacion ubicacion = ubicacionService.crearUbicacion(personaVo.getUbicacionVo());
		Login login = loginService.crearLogin(personaVo.getLoginVo());
		persona.setUbicacion(ubicacion);
		persona.setLogin(login);
		persona.setCelular(personaVo.getCelular());
		CuilValidator.esCuilValido(personaVo.getCuit(), "");
		persona.setCuit(personaVo.getCuit());
		persona.setRazonSocial(personaVo.getRazonSocial());
		persona.setFechaModi(DateUtils.fechaHoy());
		persona.setUsuarioModi(personaVo.getUsuarioModi());
		Perfil perfil = perfilService.traerPerfilPorId(personaVo.getIdPerfil());
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(ACTIVO);

		if (perfil == null) {
			throw new ObjectNotFound("Perfil");
		}
		if (estadoPersona == null) {
			throw new ObjectNotFound("EstadoPersona(1 = inactivo)");
		}

		persona.setPerfil(perfil);
		persona.setEstadoPersona(estadoPersona);
	}

	private void adaptPutVoToPersonaJuridica(PersonaJuridica persona, ReqPutPersonaJuridicaVo personaVo) {
		persona.setCelular(personaVo.getCelular());
		CuilValidator.esCuilValido(personaVo.getCuit(), "");
		persona.setCuit(personaVo.getCuit());
		persona.setRazonSocial(personaVo.getRazonSocial());
		persona.setFechaModi(DateUtils.fechaHoy());
		persona.setUsuarioModi(personaVo.getUsuarioModi());
		Perfil perfil = perfilService.traerPerfilPorId(personaVo.getIdPerfil());
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(personaVo.getIdEstadoPersona());

		if (perfil == null || estadoPersona == null) {
			throw new ObjectNotFound("Perfil/Estado Persona");
		}

		persona.setPerfil(perfil);
		persona.setEstadoPersona(estadoPersona);
	}

	public void crearToken(Persona persona, String token) {
		ResetAndValidatingToken passwordResetToken = new ResetAndValidatingToken(token, persona, expiration);

		pwdService.crearResetOrValidateToken(passwordResetToken);
	}
	
	@Transactional
	public PersonaJuridica activarPersonaJuridica(long id) {
		PersonaJuridica persona = personaRepository.findByIdPersona(id);
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(ACTIVO);
		
		if (persona == null || estadoPersona == null) {
			throw new ObjectNotFound("Persona/EstadoPersona");
		}
		log.info("Se activara persona juridica");

		persona.setEstadoPersona(estadoPersona);
		
		return personaRepository.save(persona);
		
	}

}
