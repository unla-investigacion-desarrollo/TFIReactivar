package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Random;

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
import com.unla.reactivar.models.PersonaFisica;
import com.unla.reactivar.models.ResetAndValidatingToken;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.PersonaFisicaRepository;
import com.unla.reactivar.utils.CuilValidator;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.vo.PersonaFisicaVo;
import com.unla.reactivar.vo.ReqPutPersonaFisicaVo;

@Service
@Transactional(readOnly = true)
public class PersonaFisicaService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	private static final long INACTIVO = 1;

	@Value("${recovery.password.token.duration}")
	private int expiration;

	@Autowired
	private PersonaFisicaRepository repository;

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

	@Autowired
	private MailSenderService mailSenderService;

	public PersonaFisica traerPersonaFisicaPorId(Long id) {
		log.info("Se traeran la personas fisicas por id");
		return repository.findByIdPersona(id);
	}

	public PersonaFisica traerPersonaFisicaPorDni(Long dni) {
		log.info("Se traeran persona fisica por dni");
		return repository.findByDni(dni);
	}

	public List<PersonaFisica> traerTodasPersonasFisicas() {
		log.info("Se traeran todas las personas fisica");
		return repository.findAll();
	}

	@Transactional
	public void borrarPersonaFisica(long id) {
		PersonaFisica registro = repository.findByIdPersona(id);

		if (registro == null) {
			throw new ObjectNotFound("PersonaFisica");
		}
		log.info("Se eliminara persona fisica [{}]", registro.getCuil());
		repository.deletePersona(id);
	}

	@Transactional
	public PersonaFisica crearPersonaFisica(PersonaFisicaVo personaFisicaVo) {
		PersonaFisica persona = repository.findByDni(personaFisicaVo.getDni());

		if (persona == null) {
			persona = new PersonaFisica();
		}

		adaptVoToPersonaFisica(persona, personaFisicaVo);

		try {	
			log.info("Se creara persona fisica [{}]",persona.getDni());
			persona = repository.save(persona);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		if(persona.getCuil()!=null)
			enviarEmailValidarUsuario(persona);

		return persona;
	}

	@Transactional
	public Persona actualizarPersonaFisica(Long id, ReqPutPersonaFisicaVo personaFisicaVo) {
		PersonaFisica persona = repository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		adaptPutVoToPersonaFisica(persona, personaFisicaVo);

		try {
			log.info("Se actualizara persona fisica [{}]",persona.getDni());
			persona = repository.save(persona);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return persona;
	}

	private void adaptVoToPersonaFisica(PersonaFisica persona, PersonaFisicaVo personaFisicaVo) {
		Perfil perfil = perfilService.traerPerfilPorId(personaFisicaVo.getIdPerfil());
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(INACTIVO);
		
		if (personaFisicaVo.getCuil() != null) {
			if (perfil == null) {
				throw new ObjectNotFound("Perfil");
			}
			if (estadoPersona == null) {
				throw new ObjectNotFound("EstadoPersona(1 = inactivo)");
			}
			CuilValidator.esCuilValido(personaFisicaVo.getCuil(), personaFisicaVo.getSexo());
			Ubicacion ubicacion = ubicacionService.crearUbicacion(personaFisicaVo.getUbicacionVo());
			Login login = loginService.crearLogin(personaFisicaVo.getLoginVo());
			persona.setUbicacion(ubicacion);
			persona.setLogin(login);
		}

		persona.setDni(personaFisicaVo.getDni());
		persona.setNumeroTramite(personaFisicaVo.getNumeroTramite());
		persona.setSexo(personaFisicaVo.getSexo());
		persona.setNombre(personaFisicaVo.getNombre());
		persona.setApellido(personaFisicaVo.getApellido());
		persona.setCuil(personaFisicaVo.getCuil());
		persona.setCelular(personaFisicaVo.getCelular());
		persona.setUsuarioModi(personaFisicaVo.getUsuarioModi());
		persona.setFechaModi(DateUtils.fechaHoy());
		persona.setPerfil(perfil);
		persona.setEstadoPersona(estadoPersona);
	}

	private void adaptPutVoToPersonaFisica(PersonaFisica persona, ReqPutPersonaFisicaVo personaFisicaVo) {
		Perfil perfil = perfilService.traerPerfilPorId(personaFisicaVo.getIdPerfil());
		EstadoPersona estadoPersona = estadoPersonaService
				.traerEstadoPersonaPorId(personaFisicaVo.getIdEstadoPersona());

		if (perfil == null || estadoPersona == null) {
			throw new ObjectNotFound("Perfil/Estado Persona");
		}
		CuilValidator.esCuilValido(personaFisicaVo.getCuil(), personaFisicaVo.getSexo());

		persona.setSexo(personaFisicaVo.getSexo());
		persona.setNombre(personaFisicaVo.getNombre());
		persona.setApellido(personaFisicaVo.getApellido());
		persona.setCuil(personaFisicaVo.getCuil());
		persona.setCelular(personaFisicaVo.getCelular());
		persona.setUsuarioModi(personaFisicaVo.getUsuarioModi());
		persona.setFechaModi(DateUtils.fechaHoy());
		persona.setPerfil(perfil);
		persona.setEstadoPersona(estadoPersona);
	}

	public void enviarEmailValidarUsuario(Persona persona) {

		Random rnd = new Random();
		String token = String.format("%09d", rnd.nextInt(999999999));
		crearToken(persona, token);
		log.info("Se eviara mail validar persona fisica [{}]",persona.getIdPersona());

		mailSenderService.constructValidateEmail(token, persona);
	}

	public void crearToken(Persona persona, String token) {
		ResetAndValidatingToken passwordResetToken = new ResetAndValidatingToken(token, persona, expiration);

		pwdService.crearResetOrValidateToken(passwordResetToken);
	}

}
