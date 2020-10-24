package com.unla.reactivar.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.exceptions.PdfExporterException;
import com.unla.reactivar.exceptions.UserIsAlreadyActive;
import com.unla.reactivar.models.EstadoPersona;
import com.unla.reactivar.models.OcupacionLocal;
import com.unla.reactivar.models.Perfil;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.ResetAndValidatingToken;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.PersonaRepository;
import com.unla.reactivar.utils.InformeContactoEstrechoPDFExporter;
import com.unla.reactivar.vo.CoordenadasVo;
import com.unla.reactivar.vo.PasswordRecoveryVo;

@Service
@Transactional(readOnly = false)
public class PersonaService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	private static final long INACTIVO = 1;
	private static final long ACTIVO = 2;
	private static final long BAJA = 3;

	@Value("${recovery.password.token.duration}")
	private int expiration;

	@Autowired
	private OcupacionLocalService ocupacionService;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private ResetAndValidatingTokenService pwdService;

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private EstadoPersonaService estadoPersonaService;
	
	@Autowired
	private PerfilService perfilService;

	public Persona traerPersonaPorId(long idPersona) {
		log.info("Se traera un Persona por id");
		return personaRepository.findByIdPersona(idPersona);
	}

	public Persona traerPersonaPorEmail(String email) {
		log.info("Se traeran  persona por mail [{}]", email);
		return personaRepository.findByEmail(email);
	}

	public List<Persona> traerTodos() {
		log.info("Se traera todas las personas");
		return personaRepository.findAll();
	}

	public List<Persona> traerPersonasPorEstado(long estadoPersona) {
		log.info("Se traeran todas las personas a partir del estado persona solicitado");
		return personaRepository.findAllPersonasByEstado(estadoPersona);
	}

	@Transactional
	public void borrarPersona(long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}
		log.info("Se eliminara persona [{}]", persona.getIdPersona());

		personaRepository.deletePersona(id);
	}

	public CoordenadasVo traerCoordenadas(Long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		Ubicacion ubicacion = persona.getUbicacion();
		log.info("Se traera coordenadas persona [{}]", persona.getIdPersona());

		return new CoordenadasVo(ubicacion.getLatitud(), ubicacion.getLongitud());
	}

	public void recuperarContrasenia(String email) {
		Persona persona = personaRepository.findByEmail(email);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		Random rnd = new Random();
		String token = String.format("%09d", rnd.nextInt(999999999));
		crearPasswordResetToken(persona, token);
		log.info("Se recuperara pwd persona [{}]", persona.getIdPersona());

		mailSenderService.constructResetTokenEmail(token, persona);
	}

	public void crearPasswordResetToken(Persona persona, String token) {
		ResetAndValidatingToken passwordResetToken = new ResetAndValidatingToken(token, persona, expiration);
		log.info("Se creara token pwd persona [{}]", persona.getIdPersona());

		pwdService.crearResetOrValidateToken(passwordResetToken);
	}

	public void cambiarContrasenia(String token) {
		log.info("Se cambiara contraseña");
		pwdService.validateResetOrValidatingToken(token);
	}

	public void validarEmail(String token) {
		ResetAndValidatingToken passToken = pwdService.validateResetOrValidatingToken(token);
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(ACTIVO);
		if (estadoPersona == null) {
			throw new ObjectNotFound("EstadoPersona (Activo = 2)");
		}
		Persona persona = passToken.getPersona();
		log.info("Se validara persona [{}]", persona.getIdPersona());
		persona.setEstadoPersona(estadoPersona);
	}

	public Persona guardarContrasenia(PasswordRecoveryVo passwordRecoveryVo) {
		ResetAndValidatingToken passToken = pwdService.validateResetOrValidatingToken(passwordRecoveryVo.getToken());

		Persona persona = passToken.getPersona();
		persona.getLogin().setClave(DigestUtils.sha256Hex(passwordRecoveryVo.getNewPassword()));
		log.info("Se actualizara contrasena persona [{}]", persona.getIdPersona());

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
		log.info("Se reenviara mail validacion persona [{}]", persona.getIdPersona());

		mailSenderService.constructValidateEmail(token, persona);
	}

	public void generarInformePorContactoEstrecho(HttpServletResponse response, long idPersona, Date fechaInicio,
			Date fechaFin) {
		response.setContentType("application/pdf");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=InformeContactoEstrecho-" + fechaInicio + ".pdf";
		response.setHeader(headerKey, headerValue);
		List<OcupacionLocal> ocupacionesLocal = ocupacionService.traerOcupacionEntreFechas(fechaInicio, fechaFin,
				idPersona);

		if (ocupacionesLocal.isEmpty()) {
			throw new ObjectNotFound("No existen registros en ese horario");
		}
		try {
			log.info("Se generarar informe contacto estrecho con persona [{}]", idPersona);
			InformeContactoEstrechoPDFExporter exporter = new InformeContactoEstrechoPDFExporter(ocupacionesLocal);
			exporter.export(response);
		} catch (DocumentException | IOException | WriterException e) {
			throw new PdfExporterException();
		}
	}

	@Transactional
	public Persona modificarPerfil(long idPersona, long idPerfil) {
		Perfil perfil = perfilService.traerPerfilPorId(idPerfil);
		Persona persona = personaRepository.findByIdPersona(idPersona);
		
		if (persona == null || perfil == null) {
			throw new ObjectNotFound("Persona o Perfil");
		}
		
		persona.setPerfil(perfil);
		
		return personaRepository.save(persona);
	}
	
	@Transactional
	public Persona modificarEstadoPersona(long idPersona, long idEstadoPersona) {
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(idEstadoPersona);
		Persona persona = personaRepository.findByIdPersona(idPersona);
		
		if (persona == null || estadoPersona == null) {
			throw new ObjectNotFound("Persona o EstadoPersona");
		}
		
		persona.setEstadoPersona(estadoPersona);
		
		return personaRepository.save(persona);
	}

	@Transactional
	public Persona bajaLogicaPersona(long id) {
		Persona persona = personaRepository.findByIdPersona(id);

		if (persona == null) {
			throw new ObjectNotFound("Persona");
		}

		if (ACTIVO == persona.getEstadoPersona().getIdEstadoPersona()) {
			EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(BAJA);
			if (estadoPersona == null) {
				throw new ObjectNotFound("Estado persona (baja=3)");
			}
			persona.setEstadoPersona(estadoPersona);
		}

		try {
			log.info("Se dara de baja la persona id= [{}]", persona.getIdPersona());
			persona = personaRepository.save(persona);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		log.info("Se dara de baja una persona ");

		return persona;

	}

	@Transactional
	public Persona activarPersona(long id) {
		Persona persona = personaRepository.findByIdPersona(id);
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(ACTIVO);

		if (persona == null || estadoPersona == null) {
			throw new ObjectNotFound("Persona/EstadoPersona");
		}
		log.info("Se activara persona");

		persona.setEstadoPersona(estadoPersona);

		return personaRepository.save(persona);

	}

	@Transactional
	public Persona desactivarPersona(long id) {
		Persona persona = personaRepository.findByIdPersona(id);
		EstadoPersona estadoPersona = estadoPersonaService.traerEstadoPersonaPorId(INACTIVO);

		if (persona == null || estadoPersona == null) {
			throw new ObjectNotFound("Persona/EstadoPersona");
		}
		log.info("Se desactivara persona");

		persona.setEstadoPersona(estadoPersona);

		return personaRepository.save(persona);

	}

}
