package com.unla.reactivar.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.exceptions.QrExporterException;
import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.Rubro;
import com.unla.reactivar.models.TipoEmprendimiento;
import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.repositories.EmprendimientoRepository;
import com.unla.reactivar.utils.DateUtils;
import com.unla.reactivar.utils.QREmprendimientoPDFExporter;
import com.unla.reactivar.vo.ConfiguracionLocalVo;
import com.unla.reactivar.vo.EmprendimientoVo;
import com.unla.reactivar.vo.ReqPutEmprendimientoVo;

@Service
@Transactional(readOnly = true)
public class EmprendimientoService {

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

	public Emprendimiento traerEmprendimientoPorId(Long id) {
		return repository.findByIdEmprendimiento(id);
	}

	public List<Emprendimiento> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public Emprendimiento crearEmprendimiento(EmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = new Emprendimiento();

		adaptarEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);

		try {
			emprendimiento = repository.save(emprendimiento);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}
		
		return emprendimiento;
	}

	@Transactional
	public void borrarEmprendimiento(Long id) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		repository.delete(emprendimiento);
	}

	@Transactional
	public Emprendimiento actualizarEmprendimiento(Long id, ReqPutEmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		adaptarPutEmprendimientoVoAEmprendimiento(emprendimientoVo, emprendimiento);

		try {
			emprendimiento = repository.save(emprendimiento);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}
		
		return emprendimiento;
	}

	private void adaptarEmprendimientoVoAEmprendimiento(EmprendimientoVo emprendimientoVo,
			Emprendimiento emprendimiento) {
		for (int i = 0; i < emprendimientoVo.getConfiguracionLocales().size(); i++) {
			emprendimiento.getConfiguracionLocales()
					.add(adaptarConfiguracionLocal(emprendimientoVo.getConfiguracionLocales().get(i)));
			emprendimiento.getConfiguracionLocales().get(i).setEmprendimiento(emprendimiento);
		}
		Ubicacion ubicacion = ubicacionService.crearUbicacion(emprendimientoVo.getUbicacionVo());
		emprendimiento.setUbicacion(ubicacion);
		emprendimiento.setCuit(emprendimientoVo.getCuit());
		emprendimiento.setNombre(emprendimientoVo.getNombre());
		Persona persona = personaService.traerPersonaPorId(emprendimientoVo.getIdPersona());
		Rubro rubro = rubroService.traerRubroPorId(emprendimientoVo.getIdRubro());
		TipoEmprendimiento tipoEmprendimiento = tipoEmprendimientoService
				.traerTipoEmprendimientoPorId(emprendimientoVo.getIdTipoEmprendimiento());

		if (persona == null || rubro == null || tipoEmprendimiento == null) {
			throw new ObjectNotFound("Persona, rubro o tipoEmprendimiento");
		}
		emprendimiento.setFechaModi(DateUtils.fechaHoy());
		emprendimiento.setUsuarioModi(emprendimientoVo.getUsuarioModi());
		emprendimiento.setPersona(persona);
		emprendimiento.setRubro(rubro);
		emprendimiento.setTipoEmprendimiento(tipoEmprendimiento);
		emprendimiento.setCapacidad(emprendimientoVo.getCapacidad());

	}

	private void adaptarPutEmprendimientoVoAEmprendimiento(ReqPutEmprendimientoVo emprendimientoVo,
			Emprendimiento emprendimiento) {
		emprendimiento.setCuit(emprendimientoVo.getCuit());
		emprendimiento.setNombre(emprendimientoVo.getNombre());
		Persona persona = personaService.traerPersonaPorId(emprendimientoVo.getIdPersona());
		Rubro rubro = rubroService.traerRubroPorId(emprendimientoVo.getIdRubro());
		TipoEmprendimiento tipoEmprendimiento = tipoEmprendimientoService
				.traerTipoEmprendimientoPorId(emprendimientoVo.getIdTipoEmprendimiento());

		if (persona == null || rubro == null || tipoEmprendimiento == null) {
			throw new ObjectNotFound("Persona, rubro o tipoEmprendimiento");
		}
		emprendimiento.setFechaModi(DateUtils.fechaHoy());
		emprendimiento.setUsuarioModi(emprendimientoVo.getUsuarioModi());
		emprendimiento.setPersona(persona);
		emprendimiento.setRubro(rubro);
		emprendimiento.setTipoEmprendimiento(tipoEmprendimiento);
		emprendimiento.setCapacidad(emprendimientoVo.getCapacidad());

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

		return config;
	}

	public void exportPDF(HttpServletResponse response, Long id) {
		response.setContentType("application/pdf");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=QREmprendimiento.pdf";
		response.setHeader(headerKey, headerValue);

		Emprendimiento emprendimiento = repository.findByIdEmprendimiento(id);

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		try {
			QREmprendimientoPDFExporter exporter = new QREmprendimientoPDFExporter(emprendimiento);
			exporter.export(response);
		} catch (DocumentException | IOException | WriterException e) {
			throw new QrExporterException();
		}
	}

}
