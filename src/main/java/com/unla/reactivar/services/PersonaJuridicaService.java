package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Random;

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

	private static final long INACTIVO = 1;
	
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

	@Autowired
	private MailSenderService mailSenderService;

	@Transactional
	public PersonaJuridica crearPersona(PersonaJuridicaVo personaVo) {

		PersonaJuridica persona = new PersonaJuridica();

		adaptVoToPersonaJuridica(persona, personaVo);

		try {
			persona = personaRepository.save(persona);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		enviarEmailValidarUsuario(persona); 
		
		return persona;
	}

	public PersonaJuridica traerPersonaPorId(long idPersona) {
		return personaRepository.findByIdPersona(idPersona);
	}

	public List<PersonaJuridica> traerTodos() {
		return personaRepository.findAll();
	}

	@Transactional
	public void borrarPersona(long id) {
		PersonaJuridica persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

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
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(INACTIVO);

		if (perfil == null) {
			throw new ObjectNotFound("Perfil");
		}
		if(estadoPersona == null) {
			throw new ObjectNotFound("EstadoPersona(0 = inactivo)");
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
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(INACTIVO);

		if(perfil == null || estadoPersona == null) {
			throw new ObjectNotFound("Perfil/Estado Persona");
		}
		
		persona.setPerfil(perfil);
		persona.setEstadoPersona(estadoPersona);
	}
	
	public void enviarEmailValidarUsuario(Persona persona) {
		
		Random rnd = new Random();
		String token = String.format("%09d", rnd.nextInt(999999999));
		crearToken(persona, token);

		mailSenderService.constructValidateEmail(token, persona);
	}

	public void crearToken(Persona persona, String token) {
		ResetAndValidatingToken passwordResetToken = new ResetAndValidatingToken(token, persona, expiration);
		
		pwdService.crearResetOrValidateToken(passwordResetToken);
	}

}
