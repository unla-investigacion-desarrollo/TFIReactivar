package com.unla.reactivar.services;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import com.unla.reactivar.exceptions.MaximumNumberOfImages;
import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.exceptions.PdfExporterException;
import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.EstadoEmprendimiento;
import com.unla.reactivar.models.Perfil;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.PersonaFisica;
import com.unla.reactivar.models.Rubro;
import com.unla.reactivar.models.TipoEmprendimiento;
import com.unla.reactivar.models.Turno;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.EmprendimientoRepository;
import com.unla.reactivar.repositories.TurnoRepository;
import com.unla.reactivar.utils.CuilValidator;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.utils.QREmprendimientoPDFExporter;
import com.unla.reactivar.vo.ConfiguracionLocalVo;
import com.unla.reactivar.vo.EmprendimientoVo;
import com.unla.reactivar.vo.GetResEmprendimientoVo;
import com.unla.reactivar.vo.ReqPostConfiguracionLocalVo;
import com.unla.reactivar.vo.ReqPutEmprendimientoVo;
import com.unla.reactivar.vo.UploadImageVo;
import com.unla.reactivar.vo.ValConfLocalVo;

@Service
@Transactional(readOnly = true)
public class EmprendimientoService {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	private static final String EMPRENDIMIENTO = "Emprendimiento";
	private static final long INACTIVO = 1;
	private static final long ACTIVO = 2;
	private static final long BAJA = 3;
	private static final long VENDEDOR = 3;
	private static final long USUARIO = 2;

	private static final int VERDE = 1;
	private static final int AMARILLO = 2;
	private static final int ROJO = 3;

	@Value("${server.host}")
	private String serverHost;

	@Value("${image.upload.max}")
	private int imageUploadMax;

	@Autowired
	private EmprendimientoRepository repository;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private RubroService rubroService;

	@Autowired
	private TipoEmprendimientoService tipoEmprendimientoService;

	@Autowired
	private UbicacionService ubicacionService;

	@Autowired
	private EstadoEmprendimientoService estadoEmprendimientoService;

	@Autowired
	private TurnoService turnoService;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private OcupacionLocalService ocupacionLocalService;

	@Autowired
	private ConfiguracionLocalService configuracionLocalService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private TurnoRepository turnoRepository;

	public Emprendimiento traerEmprendimientoPorId(Long id) {
		log.info("Se traera un Emprendimiento por id");
		return repository.findByIdEmprendimiento(id);
	}

	public GetResEmprendimientoVo traerEmprendimientoSinPersonaPorId(Long id) {
		log.info("Se traera un Emprendimiento por id");

		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);
		GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();

		adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, emprendimiento);

		return getResEmprendimientoVo;

	}

	public List<Emprendimiento> traerTodosEmprendimientos() {
		log.info("Se traeran todos los emprendimientos");
		return repository.findAll();
	}

	public List<GetResEmprendimientoVo> traerTodosEmprendimientosSinPersona() {
		log.info("Se traeran todos los emprendimientos");
		List<Emprendimiento> listEmprendimiento = repository.findAll();
		List<GetResEmprendimientoVo> listGetResEmprendimientoVo = new ArrayList();

		for (int i = 0; i < listEmprendimiento.size(); i++) {
			GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();
			adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, listEmprendimiento.get(i));
			listGetResEmprendimientoVo.add(getResEmprendimientoVo);

		}

		return listGetResEmprendimientoVo;

	}

	public List<Emprendimiento> traerTodosEmprendimientosInactivos() {
		log.info("Se traeran todos los emprendimientos inactivos");
		return repository.findAllInactivos();
	}

	public List<GetResEmprendimientoVo> traerTodosEmprendimientosInactivosSinPersona() {
		log.info("Se traeran todos los emprendimientos inactivos");
		List<Emprendimiento> listEmprendimiento = repository.findAllInactivos();
		List<GetResEmprendimientoVo> listGetResEmprendimientoVo = new ArrayList();
		for (int i = 0; i < listEmprendimiento.size(); i++) {
			GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();
			adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, listEmprendimiento.get(i));
			listGetResEmprendimientoVo.add(getResEmprendimientoVo);

		}

		return listGetResEmprendimientoVo;

	}

	public List<Emprendimiento> traerTodosEmprendimientosActivos() {
		log.info("Se traeran todos los emprendimientos activos");
		return repository.findAllActivos();
	}

	public List<GetResEmprendimientoVo> traerTodosEmprendimientosActivosSinPersona() {
		log.info("Se traeran todos los emprendimientos activos");
		List<Emprendimiento> listEmprendimiento = repository.findAllActivos();
		List<GetResEmprendimientoVo> listGetResEmprendimientoVo = new ArrayList();

		for (int i = 0; i < listEmprendimiento.size(); i++) {
			GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();
			adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, listEmprendimiento.get(i));
			listGetResEmprendimientoVo.add(getResEmprendimientoVo);

		}

		return listGetResEmprendimientoVo;

	}

	public List<Emprendimiento> traerTodosEmprendimientosEnBaja() {
		log.info("Se traeran todos los emprendimientos dados de baja");
		return repository.findAllBajas();
	}

	public List<GetResEmprendimientoVo> traerTodosEmprendimientosEnBajaSinPersona() {
		log.info("Se traeran todos los emprendimientos dados de baja");
		List<Emprendimiento> listEmprendimiento = repository.findAllBajas();
		List<GetResEmprendimientoVo> listGetResEmprendimientoVo = new ArrayList();

		for (int i = 0; i < listEmprendimiento.size(); i++) {
			GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();
			adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, listEmprendimiento.get(i));
			listGetResEmprendimientoVo.add(getResEmprendimientoVo);

		}

		return listGetResEmprendimientoVo;
	}

	public List<Emprendimiento> traerTodosEmprendimientosPorEstado(Long idEstadoEmprendimiento) {
		log.info("Se traeran todos los emprendimientos a partir del id del estado de emprendimiento ingresado");
		return repository.findAllEmprendimientoByEstado(idEstadoEmprendimiento);
	}

	public List<GetResEmprendimientoVo> traerTodosEmprendimientosPorEstadoSinPersona(Long idEstadoEmprendimiento) {
		log.info("Se traeran todos los emprendimientos a partir del id del estado de emprendimiento ingresado");
		List<Emprendimiento> listEmprendimiento = repository.findAllEmprendimientoByEstado(idEstadoEmprendimiento);
		List<GetResEmprendimientoVo> listGetResEmprendimientoVo = new ArrayList();

		for (int i = 0; i < listEmprendimiento.size(); i++) {
			GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();
			adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, listEmprendimiento.get(i));
			listGetResEmprendimientoVo.add(getResEmprendimientoVo);

		}

		return listGetResEmprendimientoVo;
	}

	public List<Emprendimiento> traerPorRubro(long idRubro) {
		log.info("Se traeran todos los emprendimientos por rubro [{}]", idRubro);
		return repository.traerPorRubro(idRubro);
	}

	@Transactional(readOnly = false)
	public List<GetResEmprendimientoVo> traerEmprendimientosCercanos(long idRubro, long idPersona, String cantidadKm) {
		log.info("Se traeran todos los emprendimientos por distancia");
		List<Emprendimiento> listaEmprendimientosCercanos = repository.traerEmprendimientosCercanos(idRubro, idPersona,
				cantidadKm);
		List<GetResEmprendimientoVo> listaEmprendimientosCercanosVo = new ArrayList();

		for (int i = 0; i < listaEmprendimientosCercanos.size(); i++) {
			GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();
			Emprendimiento emprendimiento = listaEmprendimientosCercanos.get(i);
			adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, emprendimiento);
			listaEmprendimientosCercanosVo.add(getResEmprendimientoVo);
		}

		return listaEmprendimientosCercanosVo;

	}

	@Transactional(readOnly = false)
	public List<GetResEmprendimientoVo> traerEmprendimientosCercanosPosActual(long idRubro, String latActual,
			String longActual, String cantidadKm) {
		log.info("Se traeran todos los emprendimientos por distancia desde la posicion actual");
		List<Emprendimiento> listaEmprendimientosCercanos = repository.traerEmprendimientosCercanosPosActual(idRubro,
				latActual, longActual, cantidadKm);
		List<GetResEmprendimientoVo> listaEmprendimientosCercanosVo = new ArrayList();

		for (int i = 0; i < listaEmprendimientosCercanos.size(); i++) {
			GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();
			Emprendimiento emprendimiento = listaEmprendimientosCercanos.get(i);
			adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, emprendimiento);
			listaEmprendimientosCercanosVo.add(getResEmprendimientoVo);
		}
		return listaEmprendimientosCercanosVo;
	}

	@Transactional
	public Emprendimiento crearEmprendimiento(EmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = new Emprendimiento();
		EstadoEmprendimiento estadoEmprendimiento = estadoEmprendimientoService.traerEstadoEmprendimientoPorId(ACTIVO);

		adaptarEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);

		if (estadoEmprendimiento == null) {
			throw new ObjectNotFound("EstadoEmprendimiento (activo = 2)");
		}

		emprendimiento.setEstadoEmprendimiento(estadoEmprendimiento);

		try {
			log.info("Se creara emprendimiento [{}]", emprendimientoVo.getNombre());
			emprendimiento = repository.save(emprendimiento);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return emprendimiento;
	}

	@Transactional
	public GetResEmprendimientoVo crearEmprendimientoSinPersona(EmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = new Emprendimiento();
		EstadoEmprendimiento estadoEmprendimiento = estadoEmprendimientoService.traerEstadoEmprendimientoPorId(ACTIVO);
		GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();

		adaptarEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);

		if (estadoEmprendimiento == null) {
			throw new ObjectNotFound("EstadoEmprendimiento (activo = 2)");
		}

		emprendimiento.setEstadoEmprendimiento(estadoEmprendimiento);

		try {
			log.info("Se creara emprendimiento [{}]", emprendimientoVo.getNombre());
			emprendimiento = repository.save(emprendimiento);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, emprendimiento);

		return getResEmprendimientoVo;
	}

	@Transactional
	public void borrarEmprendimiento(Long id) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound(EMPRENDIMIENTO);
		}

		log.info("Se eliminara emprendimiento [{}]", emprendimiento.getNombre());

		repository.delete(emprendimiento);
	}

	@Transactional
	public Emprendimiento actualizarEmprendimiento(Long id, ReqPutEmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound(EMPRENDIMIENTO);
		}

		adaptarPutEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);

		try {
			log.info("Se actualizara emprendimiento [{}]", emprendimiento.getNombre());
			emprendimiento = repository.save(emprendimiento);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return emprendimiento;
	}

//-------------------------------------------------------------------------------------------------------------------------------------------------
	@Transactional
	public GetResEmprendimientoVo actualizarEmprendimientoSinPersona(Long id, ReqPutEmprendimientoVo emprendimientoVo) {
		GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);
		List<ValConfLocalVo> listConfLocDelEmprendimiento = new ArrayList();
		List<ValConfLocalVo> listConfLocVoDelRequest = new ArrayList();
		int flag=0;
		if (emprendimiento == null) {
			throw new ObjectNotFound(EMPRENDIMIENTO);
		}

		if (turnoRepository.findByEmprendimientoFechaActual(emprendimiento).isEmpty() == false) {
			
			listConfLocDelEmprendimiento=adaptarConfiguracionLocalAValConfLocalVo(emprendimiento.getConfiguracionLocales());
			listConfLocVoDelRequest=adaptarConfiguracionLocalVoAValConfLocalVo(emprendimientoVo.getConfiguracionLocales());
						
			boolean result = new HashSet<>(listConfLocDelEmprendimiento).equals(new HashSet<>(listConfLocVoDelRequest));
			
			
			if (result == true) {
				adaptarPutEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);
			} else {
				throw new ObjectNotFound("El emprendiento tiene turnos pendientes");
			}

		}
					
		else {
			adaptarPutEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);
		}

		try {
			log.info("Se actualizara emprendimiento [{}]", emprendimiento.getNombre());
			emprendimiento = repository.save(emprendimiento);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, emprendimiento);

		return getResEmprendimientoVo;
	}

	
	private List<ValConfLocalVo> adaptarConfiguracionLocalAValConfLocalVo(List<ConfiguracionLocal> listaConfiguracionLocal){
		List<ValConfLocalVo> listaValConfLocalVo = new ArrayList();
		for(int i=0;i < listaConfiguracionLocal.size();i++) {
			ValConfLocalVo valConfLocalVo= new ValConfLocalVo();
			valConfLocalVo.setDiaSemana(listaConfiguracionLocal.get(i).getDiaSemana());
			valConfLocalVo.setIntervaloTurnos(listaConfiguracionLocal.get(i).getIntervaloTurnos());
			valConfLocalVo.setTurno1Desde(listaConfiguracionLocal.get(i).getTurno1Desde());
			valConfLocalVo.setTurno1Hasta(listaConfiguracionLocal.get(i).getTurno1Hasta());
			valConfLocalVo.setTurno2Desde(listaConfiguracionLocal.get(i).getTurno2Desde());
			valConfLocalVo.setTurno2Hasta(listaConfiguracionLocal.get(i).getTurno2Hasta());
			listaValConfLocalVo.add(valConfLocalVo);
		}
		
		return listaValConfLocalVo;
		
	}
	
	private List<ValConfLocalVo> adaptarConfiguracionLocalVoAValConfLocalVo(List<ConfiguracionLocalVo> listaConfiguracionLocalVo){
		List<ValConfLocalVo> listaValConfLocalVo = new ArrayList();
		for(int i=0;i < listaConfiguracionLocalVo.size();i++) {
			ValConfLocalVo valConfLocalVo= new ValConfLocalVo();
			valConfLocalVo.setDiaSemana(listaConfiguracionLocalVo.get(i).getDiaSemana());
			valConfLocalVo.setIntervaloTurnos(listaConfiguracionLocalVo.get(i).getIntervaloTurnos());
			valConfLocalVo.setTurno1Desde(listaConfiguracionLocalVo.get(i).getTurno1Desde());
			valConfLocalVo.setTurno1Hasta(listaConfiguracionLocalVo.get(i).getTurno1Hasta());
			valConfLocalVo.setTurno2Desde(listaConfiguracionLocalVo.get(i).getTurno2Desde());
			valConfLocalVo.setTurno2Hasta(listaConfiguracionLocalVo.get(i).getTurno2Hasta());
			listaValConfLocalVo.add(valConfLocalVo);
		}
		
		return listaValConfLocalVo;
		
	}
	
//-------------------------------------------------------------------------------------------------------------------------------------------------
	private void adaptarEmprendimientoVoAEmprendimiento(EmprendimientoVo emprendimientoVo,
			Emprendimiento emprendimiento) {
		for (int i = 0; i < emprendimientoVo.getConfiguracionLocales().size(); i++) {
			emprendimiento.getConfiguracionLocales()
					.add(adaptarConfiguracionLocal(emprendimientoVo.getConfiguracionLocales().get(i)));
			emprendimiento.getConfiguracionLocales().get(i).setEmprendimiento(emprendimiento);
		}
		Ubicacion ubicacion = ubicacionService.crearUbicacion(emprendimientoVo.getUbicacionVo());
		emprendimiento.setUbicacion(ubicacion);
		CuilValidator.esCuilValido(emprendimientoVo.getCuit(), "", "");
		emprendimiento.setCuit(emprendimientoVo.getCuit());
		emprendimiento.setNombre(emprendimientoVo.getNombre());
		Persona persona = personaService.traerPersonaPorId(emprendimientoVo.getIdPersona());
		Rubro rubro = rubroService.traerRubroPorId(emprendimientoVo.getIdRubro());
		TipoEmprendimiento tipoEmprendimiento = tipoEmprendimientoService
				.traerTipoEmprendimientoPorId(emprendimientoVo.getIdTipoEmprendimiento());
		Perfil perfil = perfilService.traerPerfilPorId(VENDEDOR);

		if (persona == null || rubro == null || tipoEmprendimiento == null || perfil == null) {
			throw new ObjectNotFound("Persona, rubro, tipoEmprendimiento o Perfil 3 Vendedor");
		}

		emprendimiento.setTelefono(emprendimientoVo.getTelefono());
		emprendimiento.setFechaModi(DateUtils.fechaHoy());
		emprendimiento.setUsuarioModi(emprendimientoVo.getUsuarioModi());
		emprendimiento.setPersona(persona);
		emprendimiento.setRubro(rubro);
		emprendimiento.setTipoEmprendimiento(tipoEmprendimiento);
		emprendimiento.setCapacidad(emprendimientoVo.getCapacidad());
		emprendimiento.setAceptaFoto(emprendimientoVo.isAceptaFoto());
		emprendimiento.getPersona().setPerfil(perfil);
	}

	private void adaptarPutEmprendimientoVoAEmprendimiento(ReqPutEmprendimientoVo emprendimientoVo,
			Emprendimiento emprendimiento) {
		CuilValidator.esCuilValido(emprendimientoVo.getCuit(), "", "");
		emprendimiento.setCuit(emprendimientoVo.getCuit());
		emprendimiento.setNombre(emprendimientoVo.getNombre());
		Persona persona = personaService.traerPersonaPorId(emprendimientoVo.getIdPersona());
		Rubro rubro = rubroService.traerRubroPorId(emprendimientoVo.getIdRubro());
		TipoEmprendimiento tipoEmprendimiento = tipoEmprendimientoService
				.traerTipoEmprendimientoPorId(emprendimientoVo.getIdTipoEmprendimiento());
		EstadoEmprendimiento estadoEmprendimiento = estadoEmprendimientoService
				.traerEstadoEmprendimientoPorId(emprendimientoVo.getIdEstadoEmprendimiento());

		if (persona == null || rubro == null || tipoEmprendimiento == null) {
			throw new ObjectNotFound("Persona, estadoEmprendimiento, rubro o tipoEmprendimiento");
		}

		emprendimiento.setTelefono(emprendimientoVo.getTelefono());
		emprendimiento.setFechaModi(DateUtils.fechaHoy());
		emprendimiento.setUsuarioModi(emprendimientoVo.getUsuarioModi());
		emprendimiento.setPersona(persona);
		emprendimiento.setRubro(rubro);
		emprendimiento.setTipoEmprendimiento(tipoEmprendimiento);
		emprendimiento.setCapacidad(emprendimientoVo.getCapacidad());
		emprendimiento.setEstadoEmprendimiento(estadoEmprendimiento);
		emprendimiento.setAceptaFoto(emprendimientoVo.isAceptaFoto());

		ubicacionService.borrarUbicacion(emprendimiento.getUbicacion().getIdUbicacion());

		Ubicacion ubicacion = ubicacionService.crearUbicacion(emprendimientoVo.getUbicacionVo());
		emprendimiento.setUbicacion(ubicacion);

		configuracionLocalService.borrarListaConfiguracionLocal(emprendimiento.getIdEmprendimiento());

		for (int i = 0; i < emprendimientoVo.getConfiguracionLocales().size(); i++) {

			ReqPostConfiguracionLocalVo reqPostConfiguracionLocalVo = new ReqPostConfiguracionLocalVo();
			reqPostConfiguracionLocalVo.setDiaSemana(emprendimientoVo.getConfiguracionLocales().get(i).getDiaSemana());
			reqPostConfiguracionLocalVo.setIdEmprendimiento(emprendimiento.getIdEmprendimiento());
			reqPostConfiguracionLocalVo
					.setIntervaloTurnos(emprendimientoVo.getConfiguracionLocales().get(i).getIntervaloTurnos());
			reqPostConfiguracionLocalVo
					.setTiempoAtencion(emprendimientoVo.getConfiguracionLocales().get(i).getTiempoAtencion());
			reqPostConfiguracionLocalVo
					.setTurno1Desde(emprendimientoVo.getConfiguracionLocales().get(i).getTurno1Desde());
			reqPostConfiguracionLocalVo
					.setTurno1Hasta(emprendimientoVo.getConfiguracionLocales().get(i).getTurno1Hasta());
			reqPostConfiguracionLocalVo
					.setTurno2Desde(emprendimientoVo.getConfiguracionLocales().get(i).getTurno2Desde());
			reqPostConfiguracionLocalVo
					.setTurno2Hasta(emprendimientoVo.getConfiguracionLocales().get(i).getTurno2Hasta());
			reqPostConfiguracionLocalVo
					.setUsuarioModi(emprendimientoVo.getConfiguracionLocales().get(i).getUsuarioModi());
			configuracionLocalService.crearConfiguracion(reqPostConfiguracionLocalVo);

		}
	

	}

	private ConfiguracionLocal adaptarConfiguracionLocal(ConfiguracionLocalVo configuracionLocales) {

		ConfiguracionLocal config = new ConfiguracionLocal();
		config.setDiaSemana(configuracionLocales.getDiaSemana());
		config.setIntervaloTurnos(configuracionLocales.getIntervaloTurnos());
		config.setTiempoAtencion(configuracionLocales.getTiempoAtencion());
		config.setTurno1Desde(configuracionLocales.getTurno1Desde());
		config.setTurno1Hasta(configuracionLocales.getTurno1Hasta());
		config.setTurno2Desde(configuracionLocales.getTurno2Desde());
		config.setTurno2Hasta(configuracionLocales.getTurno2Hasta());
		config.setFechaModi(DateUtils.fechaHoy());
		config.setUsuarioModi(configuracionLocales.getUsuarioModi());

		return config;
	}

	public void exportPDF(HttpServletResponse response, Long id) {
		response.setContentType("application/pdf");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=QREmprendimiento.pdf";
		response.setHeader(headerKey, headerValue);

		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound(EMPRENDIMIENTO);
		}

		try {
			log.info("Se exportara qr para emprendimiento [{}]", emprendimiento.getNombre());
			QREmprendimientoPDFExporter exporter = new QREmprendimientoPDFExporter(emprendimiento, serverHost);
			exporter.export(response);
		} catch (DocumentException | IOException | WriterException e) {
			throw new PdfExporterException();
		}
	}

	@Transactional
	public Emprendimiento bajaLogicaEmprendimiento(Long id) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound(EMPRENDIMIENTO);
		}

		if (ACTIVO == emprendimiento.getEstadoEmprendimiento().getIdEstadoEmprendimiento()) {
			EstadoEmprendimiento estadoEmprendimiento = estadoEmprendimientoService
					.traerEstadoEmprendimientoPorId(BAJA);
			if (estadoEmprendimiento == null) {
				throw new ObjectNotFound("EstadoEmprendimiento (baja = 3)");
			}
			emprendimiento.setEstadoEmprendimiento(estadoEmprendimiento);
			Perfil perfil = perfilService.traerPerfilPorId(USUARIO);
			emprendimiento.getPersona().setPerfil(perfil);
		}

		try {
			log.info("Se dara de baja emprendimiento [{}]", emprendimiento.getNombre());
			emprendimiento = repository.save(emprendimiento);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}
		return emprendimiento;
	}

	@Transactional
	public GetResEmprendimientoVo bajaLogicaEmprendimientoSinPersona(Long id) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);
		GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();

		if (emprendimiento == null) {
			throw new ObjectNotFound(EMPRENDIMIENTO);
		}

		if (ACTIVO == emprendimiento.getEstadoEmprendimiento().getIdEstadoEmprendimiento()) {
			EstadoEmprendimiento estadoEmprendimiento = estadoEmprendimientoService
					.traerEstadoEmprendimientoPorId(BAJA);
			if (estadoEmprendimiento == null) {
				throw new ObjectNotFound("EstadoEmprendimiento (baja = 3)");
			}
			emprendimiento.setEstadoEmprendimiento(estadoEmprendimiento);
			Perfil perfil = perfilService.traerPerfilPorId(USUARIO);
			emprendimiento.getPersona().setPerfil(perfil);
		}

		try {
			log.info("Se dara de baja emprendimiento [{}]", emprendimiento.getNombre());
			emprendimiento = repository.save(emprendimiento);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, emprendimiento);
		return getResEmprendimientoVo;
	}

	@Transactional
	public Emprendimiento habilitarEmprendimiento(Long id) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);
		EstadoEmprendimiento estadoEmprendimiento = estadoEmprendimientoService.traerEstadoEmprendimientoPorId(ACTIVO);

		if (emprendimiento == null || estadoEmprendimiento == null) {
			throw new ObjectNotFound(EMPRENDIMIENTO + " EstadoEmprendimiento activo=2");
		}
		log.info("Se habilitara emprendimiento [{}]", emprendimiento.getNombre());
		emprendimiento.setEstadoEmprendimiento(estadoEmprendimiento);

		return repository.save(emprendimiento);
	}

	@Transactional
	public GetResEmprendimientoVo habilitarEmprendimientoSinPersona(Long id) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);
		EstadoEmprendimiento estadoEmprendimiento = estadoEmprendimientoService.traerEstadoEmprendimientoPorId(ACTIVO);
		GetResEmprendimientoVo getResEmprendimientoVo = new GetResEmprendimientoVo();

		if (emprendimiento == null || estadoEmprendimiento == null) {
			throw new ObjectNotFound(EMPRENDIMIENTO + " EstadoEmprendimiento activo=2");
		}
		log.info("Se habilitara emprendimiento [{}]", emprendimiento.getNombre());
		emprendimiento.setEstadoEmprendimiento(estadoEmprendimiento);
		repository.save(emprendimiento);

		adaptarEmprendimientoAGetResEmprendimientoVo(getResEmprendimientoVo, emprendimiento);
		return getResEmprendimientoVo;
	}

	public List<Turno> traerTurnosPorEmprendimiento(long idEmp, long idEst) {
		List<Turno> turnos = new ArrayList<Turno>();
		turnos = turnoService.traerTurnosPorEmprendimiento(idEmp, idEst);
		return turnos;
	}

	public String verificarEmprendimiento(Long id) {

		String respuesta = "No informo horarios";

		GregorianCalendar diaHoy = new GregorianCalendar();
		String diahoy = traerDiaDeLaSemana(diaHoy);
		String horahoy = traerHora(diaHoy);

		log.info("Hoy es  [{}]", diahoy);
		List<ConfiguracionLocal> ListaConfigLocal = traerConfiguracionLocal(id);

		for (int i = 0; i < ListaConfigLocal.size(); i++) {

			String diaLocal = ListaConfigLocal.get(i).getDiaSemana();

			while (diahoy.compareTo(diaLocal) == 0) {

				String turno1desde = ListaConfigLocal.get(i).getTurno1Desde();
				String turno1hasta = ListaConfigLocal.get(i).getTurno1Hasta();
				String turno2desde = ListaConfigLocal.get(i).getTurno2Desde();
				String turno2hasta = ListaConfigLocal.get(i).getTurno2Hasta();

				if (turno1desde.compareTo(horahoy) == 0
						|| turno1desde.compareTo(horahoy) < 0 && turno1hasta.compareTo(horahoy) > 0
						|| turno2desde.compareTo(horahoy) == 0
						|| turno2desde.compareTo(horahoy) < 0 && turno2hasta.compareTo(horahoy) > 0) {
					log.info("compara la hora y esta abierto");
					respuesta = "ABIERTO";
				} else {
					log.info("esta cerrado ahora el emprendimiento");
					respuesta = "CERRADO";
				}
				diahoy = "Salir";
			}
		}
		return respuesta;
	}

	public List<ConfiguracionLocal> traerConfiguracionLocal(Long id) {
		log.info("Se traera la configuración local del emprendimiento del ID elegido");

		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);
		List<ConfiguracionLocal> configuracionLocal = new ArrayList<ConfiguracionLocal>();
		configuracionLocal = emprendimiento.getConfiguracionLocales();

		return configuracionLocal;
	}

	public static String traerHora(GregorianCalendar diaHoy) {

		int hora = diaHoy.get(Calendar.HOUR_OF_DAY);
		int minutos = diaHoy.get(Calendar.MINUTE);

		return (hora + ":" + minutos);
	}

	public static String traerDiaDeLaSemana(GregorianCalendar diaHoy) {
		String dia = "";

		switch (diaHoy.get(Calendar.DAY_OF_WEEK)) {

		case 1:
			dia = "Domingo";
			break;

		case 2:
			dia = "Lunes";
			break;

		case 3:
			dia = "Martes";
			break;

		case 4:
			dia = "Miercoles";
			break;

		case 5:
			dia = "Jueves";
			break;

		case 6:
			dia = "Viernes";
			break;

		case 7:
			dia = "Sabado";
			break;
		}

		return dia;
	}

	private void adaptarEmprendimientoAGetResEmprendimientoVo(GetResEmprendimientoVo getResEmprendimientoVo,
			Emprendimiento emprendimiento) {

		boolean usaTurno = false;

		getResEmprendimientoVo.setIdEmprendimiento(emprendimiento.getIdEmprendimiento());
		getResEmprendimientoVo.setNombre(emprendimiento.getNombre());
		getResEmprendimientoVo.setCuit(emprendimiento.getCuit());
		getResEmprendimientoVo.setUsuarioModi(emprendimiento.getUsuarioModi());
		getResEmprendimientoVo.setFechaModi(emprendimiento.getFechaModi());
		getResEmprendimientoVo.setCapacidad(emprendimiento.getCapacidad());
		getResEmprendimientoVo.setAceptaFoto(emprendimiento.isAceptaFoto());
		getResEmprendimientoVo.setEstadoEmprendimiento(emprendimiento.getEstadoEmprendimiento());
		getResEmprendimientoVo.setTipoEmprendimiento(emprendimiento.getTipoEmprendimiento());
		getResEmprendimientoVo.setUbicacion(emprendimiento.getUbicacion());
		getResEmprendimientoVo.setRubro(emprendimiento.getRubro());
		getResEmprendimientoVo.setConfiguracionesLocal(emprendimiento.getConfiguracionLocales());
		getResEmprendimientoVo.setTelefono(emprendimiento.getTelefono());
		getResEmprendimientoVo.setNroColor(traerNroColor(emprendimiento.getCapacidad(),
				ocupacionLocalService.traerCantidadClientes(emprendimiento.getIdEmprendimiento())));
		getResEmprendimientoVo.setImagenes(emprendimiento.getImagenes());
		usaTurno = repository.usaTurno(emprendimiento);

		getResEmprendimientoVo.setUsaTurnos(usaTurno);

		getResEmprendimientoVo.setCantPersonasEnLocal(
				ocupacionLocalService.traerCantidadClientes(emprendimiento.getIdEmprendimiento()));

	}

	public static int traerNroColor(int capacidad, int cantClientes) {
		int nroColor = 0;
		float porcCapacidadOcupada = (cantClientes * 100) / capacidad;
		if (capacidad <= 1) {
			if (cantClientes <= 1) {
				nroColor = VERDE;
			}
			if (cantClientes > 1 && cantClientes <= 3) {
				nroColor = AMARILLO;
			}
			if (cantClientes > 3) {
				nroColor = ROJO;
			}

		}

		if (capacidad > 1 && capacidad <= 4) {
			if (cantClientes <= 2) {
				nroColor = VERDE;
			}
			if (cantClientes > 2 && cantClientes <= 4) {
				nroColor = AMARILLO;
			}
			if (cantClientes > 4) {
				nroColor = ROJO;
			}
		}

		if (capacidad > 4) {
			if (porcCapacidadOcupada <= 70) {
				nroColor = VERDE;
			}
			if (porcCapacidadOcupada > 70 && porcCapacidadOcupada < 100) {
				nroColor = AMARILLO;
			}
			if (porcCapacidadOcupada >= 100) {
				nroColor = ROJO;
			}
		}

		return nroColor;

	}

	@SuppressWarnings("resource")
	@Transactional
	public void uploadImage(UploadImageVo uploadImageVo) {

		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(uploadImageVo.getIdEmprendimiento());

		if (emprendimiento.getImagenes().size() >= imageUploadMax) {
			throw new MaximumNumberOfImages();
		}

		Random rnd = new Random();
		String randomStr = String.format("%09d", rnd.nextInt(999999999));

		String nombreImagen = emprendimiento.getNombre() + randomStr;

		imageService.crearImagen(nombreImagen, emprendimiento.getIdEmprendimiento(), uploadImageVo.getImageBase64());

	}

}
