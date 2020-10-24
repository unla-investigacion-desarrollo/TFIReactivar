package com.unla.reactivar.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.Turno;
import com.unla.reactivar.services.EmprendimientoService;
import com.unla.reactivar.vo.EmprendimientoVo;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.GetResEmprendimientoVo;
import com.unla.reactivar.vo.ReqPutEmprendimientoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/emprendimiento")
@Api(tags = "Emprendimiento")
public class EmprendimientoController {

	@Autowired
	private EmprendimientoService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Emprendimientos", notes = "Servicio para listar todos los Emprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimientos encontrados"),
			@ApiResponse(code = 404, message = "Emprendimientos no encontrados") })
	public List<GetResEmprendimientoVo> traerTodosEmprendimientos() {
		return service.traerTodosEmprendimientosSinPersona();
	}

	@GetMapping("/inactivos")
	@ApiOperation(value = "Listar todos los Emprendimientos inactivos", notes = "Servicio para listar todos los Emprendimientos inactivos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimientos inactivos encontrados"),
			@ApiResponse(code = 404, message = "Emprendimientos no encontrados") })
	public List<GetResEmprendimientoVo> traerTodosEmprendimientosInactivos() {
		return service.traerTodosEmprendimientosInactivosSinPersona();
	}

	@GetMapping("/activos")
	@ApiOperation(value = "Listar todos los Emprendimientos activos", notes = "Servicio para listar todos los Emprendimientos activos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimientos activos encontrados"),
			@ApiResponse(code = 404, message = "Emprendimientos no encontrados") })
	public List<GetResEmprendimientoVo> traerTodosEmprendimientosActivos() {
		return service.traerTodosEmprendimientosActivosSinPersona();
	}

	@GetMapping("/enBaja")
	@ApiOperation(value = "Listar todos los Emprendimientos dados de baja", notes = "Servicio para listar todos los Emprendimientos dados de baja")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimientos dados de baja encontrados"),
			@ApiResponse(code = 404, message = "Emprendimientos no encontrados") })
	public List<GetResEmprendimientoVo> traerTodosEmprendimientosEnBaja() {
		return service.traerTodosEmprendimientosEnBajaSinPersona();
	}

	@GetMapping("empXEstado/{idEstadoEmprendimiento}")
	@ApiOperation(value = "Listar todos los Emprendimientos segun su Estado", notes = "Servicio para listar todos los Emprendimientos a partir del ID del Estado de emprendimiento deseado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimientos encontrados"),
			@ApiResponse(code = 404, message = "Emprendimientos no encontrados") })
	public List<GetResEmprendimientoVo> traerTodosEmprendimientosPorEstado(
			@PathVariable("idEstadoEmprendimiento") Long id) {
		return service.traerTodosEmprendimientosPorEstadoSinPersona(id);
	}

	@GetMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Mostrar un Emprendimiento por ID", notes = "Servicio para mostrar un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento encontrado"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public GetResEmprendimientoVo traerEmprendimiento(@PathVariable("idEmprendimiento") long id) {
		return service.traerEmprendimientoSinPersonaPorId(id);
	}

	@GetMapping("/{idEmprendimiento}/turnos")
	@ApiOperation(value = "Mostrar un Emprendimiento por ID", notes = "Servicio para mostrar un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento encontrado"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public List<Turno> traerTurnosDeEmprendimiento(@PathVariable("idEmprendimiento") long id,
			@RequestParam long estadoTurno) {
		return service.traerTurnosPorEmprendimiento(id, estadoTurno);
	}

	@GetMapping("{idRubro}/{idPersona}/{cantidadKm}/traerPorRubroYKm")
	@ApiOperation(value = "Mostrar emprendimientos activos de un rubro especifico bajo la distancia elegida", notes = "Service para mostrar emprendimientos activos de un rubro especifico bajo la distancia elegida")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimientos encontrados"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<List<GetResEmprendimientoVo>> traerEmprendimientosCercanos(
			@PathVariable("idRubro") long idRubro, @PathVariable("idPersona") long idPersona,
			@PathVariable("cantidadKm") String cantidadKm) {
		List<GetResEmprendimientoVo> traerEmprendimientosCercanos = service.traerEmprendimientosCercanos(idRubro,
				idPersona, cantidadKm);

		return new ResponseEntity<>(traerEmprendimientosCercanos, HttpStatus.OK);
	}

	@GetMapping("{idRubro}/{latActual}/{longActual}/{cantidadKm}/traerPorRubroKmLatLong")
	@ApiOperation(value = "Mostrar emprendimientos de un rubro especifico bajo la distancia elegida en la posición actual", notes = "Service para mostrar emprendimientos de un rubro especifico bajo la distancia elegida en la posición actual (  Latitud y Longitud )")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimientos encontrados"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<List<GetResEmprendimientoVo>> traerEmprendimientosCercanosPosActual(
			@PathVariable("idRubro") long idRubro, @PathVariable(value = "latActual") String latActual,
			@PathVariable(value = "longActual") String longActual, @PathVariable("cantidadKm") String cantidadKm) {
		List<GetResEmprendimientoVo> traerEmprendimientosCercanosPosActual = service
				.traerEmprendimientosCercanosPosActual(idRubro, latActual, longActual, cantidadKm);

		return new ResponseEntity<>(traerEmprendimientosCercanosPosActual, HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "Crear Emprendimiento", notes = "Servicio para crear un Emprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimiento creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Emprendimiento") })
	public ResponseEntity<GetResEmprendimientoVo> crearEmprendimiento(@RequestBody EmprendimientoVo emprendimientoVo) {

		return new ResponseEntity<>(service.crearEmprendimientoSinPersona(emprendimientoVo), HttpStatus.CREATED);
	}

	@DeleteMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Eliminar emprendimiento por ID", notes = "Servicio para eliminar un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento eliminado con exito"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<Empty> eliminarEmprendimiento(@PathVariable("idEmprendimiento") long id) {

		service.borrarEmprendimiento(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Modificar un Emprendimiento por ID", notes = "Servicio para modificar un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento modificado correctamente"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<GetResEmprendimientoVo> updateEmprendimiento(@PathVariable("idEmprendimiento") long id,
			@RequestBody ReqPutEmprendimientoVo emprendimientoVo) {

		return new ResponseEntity<>(service.actualizarEmprendimientoSinPersona(id, emprendimientoVo), HttpStatus.OK);
	}

	@GetMapping("/{idEmprendimiento}/exportpdf")
	@ApiOperation(value = "Exportar QR en PDF", notes = "Servicio para exportar el QR del Emprendimiento en formato PDF a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "PDF exportado exitosamente"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado, no se pudo exportar") })
	public ResponseEntity<Empty> exportToPDF(HttpServletResponse response, @PathVariable("idEmprendimiento") long id) {
		service.exportPDF(response, id);
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PatchMapping("/{idEmprendimiento}/bajaLogica")
	@ApiOperation(value = "Baja logica de un Emprendimiento por ID", notes = "Servicio para dar de baja a un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento dado de baja correctamente"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<GetResEmprendimientoVo> bajaLogicaEmprendimiento(@PathVariable("idEmprendimiento") long id) {

		return new ResponseEntity<>(service.bajaLogicaEmprendimientoSinPersona(id), HttpStatus.OK);
	}

	@PatchMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Habilitar un Emprendimiento por ID", notes = "Servicio para habilitar un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento habilitado correctamente"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<GetResEmprendimientoVo> habilitarEmprendimiento(@PathVariable("idEmprendimiento") long id) {

		return new ResponseEntity<>(service.habilitarEmprendimientoSinPersona(id), HttpStatus.OK);
	}

	@GetMapping("/{idEmprendimiento}/traerConfiguracionLocal")
	@ApiOperation(value = "Listar configuraciones del local de un Emprendimiento", notes = "Servicio para Listar Configuraciones de Local de un emprendimiento")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Emprendimiento encontrado con su Lista de configuración"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public List<ConfiguracionLocal> traerConfiguracionLocal(@PathVariable("idEmprendimiento") long id) {
		List<ConfiguracionLocal> traerConfiguracionLocal = service.traerConfiguracionLocal(id);

		return traerConfiguracionLocal;
	}

	@GetMapping("/{idEmprendimiento}/verificarEmprendimiento")
	@ApiOperation(value = "Muestra el estado actual (abierto/cerrado) del emprendimiento", notes = "Servicio para mostrar el estado actual (abierto/cerrado) del emprendimiento segun su Configuración Local")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento encontrado"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public String verificarEmprendimiento(@PathVariable("idEmprendimiento") long id) {
		return service.verificarEmprendimiento(id);
	}

}
