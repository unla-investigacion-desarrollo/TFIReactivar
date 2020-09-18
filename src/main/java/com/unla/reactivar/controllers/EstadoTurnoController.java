package com.unla.reactivar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.EstadoTurno;
import com.unla.reactivar.services.EstadoTurnoService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.EstadoTurnoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/estadoTurno")
@Api(tags = "EstadoTurno")
public class EstadoTurnoController {

	@Autowired
	private EstadoTurnoService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Estados Turnos", notes = "Servicio para listar todos los Estados Turnos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Estados Turnos encontrados"),
			@ApiResponse(code = 404, message = "Estados Turnos no encontrados") })
	public List<EstadoTurno> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idEstadoTurno}")
	@ApiOperation(value = "Mostrar un Estado Turno por ID", notes = "Servicio para mostrar un Estado Turno a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Estado Turno encontrado"),
			@ApiResponse(code = 404, message = "Estado Turno no encontrado") })
	public EstadoTurno traerEstadoTurno(@PathVariable("idEstadoTurno") long id) {
		return service.traerEstadoTurnoPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Estado Turno", notes = "Servicio para crear un Estado Turno")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Estado Turno creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Estado Turno") })
	public ResponseEntity<EstadoTurno> crearEstadoTurno(@RequestBody EstadoTurnoVo estadoTurnoVo) {
		EstadoTurno estadoTurno = service.crearEstadoTurno(estadoTurnoVo);

		return new ResponseEntity<>(estadoTurno, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idEstadoTurno}")
	@ApiOperation(value = "Eliminar Estado Turno por ID", notes = "Servicio para eliminar un Estado Turno a partir de un  ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Estado Turno eliminado con exito"),
			@ApiResponse(code = 404, message = "Estado Turno no encontrado") })
	public ResponseEntity<Empty> eliminarEstadoTurno(@PathVariable("idEstadoTurno") long id) {

		service.borrarEstadoTurno(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idEstadoTurno}")
	@ApiOperation(value = "Modificar Estado Turno por ID", notes = "Servicio para modificar un Estado Turno a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Estado Turno modificado exitosamente"),
			@ApiResponse(code = 404, message = "Estado Turno no encontrado") })
	public ResponseEntity<EstadoTurno> updateEstadoTurno(@PathVariable("idEstadoTurno") Long id,
			EstadoTurnoVo estadoTurnoVo) {

		return new ResponseEntity<>(service.actualizarEstadoTurno(id, estadoTurnoVo), HttpStatus.OK);
	}

}
