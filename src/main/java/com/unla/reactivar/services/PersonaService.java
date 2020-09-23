package com.unla.reactivar.services;

import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.exceptions.UserIsAlreadyActive;
import com.unla.reactivar.models.EstadoPersona;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.ResetAndValidatingToken;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.PersonaRepository;
import com.unla.reactivar.vo.CoordenadasVo;
import com.unla.reactivar.vo.PasswordRecoveryVo;

@Service
@Transactional(readOnly = false)
public class PersonaService {

	private static final long ACTIVO = 2;

	@Value("${recovery.password.token.duration}")
	private int expiration;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private ResetAndValidatingTokenService pwdService;

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private EstadoPersonaService estadoPersonaService;

	public Persona traerPersonaPorId(long idPersona) {
		return personaRepository.findByIdPersona(idPersona);
	}

	public Persona traerPersonaPorEmail(String email) {
		return personaRepository.findByEmail(email);
	}

	public List<Persona> traerTodos() {
		return personaRepository.findAll();
	}

	@Transactional
	public void borrarPersona(long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		personaRepository.deletePersona(id);
	}

	public CoordenadasVo traerCoordenadas(Long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		Ubicacion ubicacion = persona.getUbicacion();

		return new CoordenadasVo(ubicacion.getLatitud(), ubicacion.getLongitud());
	}

	public void recuperarContrasenia(long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		Random rnd = new Random();
		String token = String.format("%09d", rnd.nextInt(999999999));
		crearPasswordResetToken(persona, token);

		mailSenderService.constructResetTokenEmail(token, persona);
	}

	public void crearPasswordResetToken(Persona persona, String token) {
		ResetAndValidatingToken passwordResetToken = new ResetAndValidatingToken(token, persona, expiration);

		pwdService.crearResetOrValidateToken(passwordResetToken);
	}

	public void cambiarContrasenia(String token) {
		pwdService.validateResetOrValidatingToken(token);
	}

	public void validarEmail(String token) {
		ResetAndValidatingToken passToken = pwdService.validateResetOrValidatingToken(token);
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(ACTIVO);
		if (estadoPersona == null) {
			throw new ObjectNotFound("EstadoPersona (Activo = 2)");
		}
		Persona persona = passToken.getPersona();
		persona.setEstadoPersona(estadoPersona);
	}

	public Persona guardarContrasenia(PasswordRecoveryVo passwordRecoveryVo) {
		ResetAndValidatingToken passToken = pwdService.validateResetOrValidatingToken(passwordRecoveryVo.getToken());

		Persona persona = passToken.getPersona();
		persona.getLogin().setClave(DigestUtils.sha256Hex(passwordRecoveryVo.getNewPassword()));

		return personaRepository.save(persona);
	}

	public void reenviarValidarEmail(String email) {
		Persona persona = personaRepository.findByEmail(email);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		if (persona.getEstadoPersona().getIdEstadoPersona() == ACTIVO) {
			throw new UserIsAlreadyActive();
		}

		Random rnd = new Random();
		String token = String.format("%09d", rnd.nextInt(999999999));
		crearPasswordResetToken(persona, token);

		mailSenderService.constructValidateEmail(token, persona);
	}

}
