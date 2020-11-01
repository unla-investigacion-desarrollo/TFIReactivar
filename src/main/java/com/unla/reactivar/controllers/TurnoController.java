package com.unla.reactivar.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.unla.reactivar.models.Turno;
import com.unla.reactivar.services.TurnoService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.GetResTurnoVo;
import com.unla.reactivar.vo.ReqPatchTurnoVo;
import com.unla.reactivar.vo.TurnoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/turno")
@Api(tags = "Turno")
public class TurnoController {

	@Autowired
	private TurnoService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Turnos", notes = "Servicio para listar todos los Turnos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Turnos encontrados"),
			@ApiResponse(code = 404, message = "Turnos no encontrados") })
	public List<Turno> traerTodosTurnos() {
		return service.traerTodosTurnos();
	}

	@GetMapping("/disponibles")
	public Map<String, List<String>> traerTodosTurnosDisponiblesXFecha(@RequestParam long idEmprendimiento, @RequestParam String fecha) {
		return service.traerTurnosDisponiblesXFecha(idEmprendimiento, fecha);
	}

	@GetMapping("/{idTurno}")
	@ApiOperation(value = "Mostrar un Turno por ID", notes = "Servicio para mostrar un Turno a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Turno encontrado"),
			@ApiResponse(code = 404, message = "Turno no encontrado") })
	public Turno traerTurno(@PathVariable("idTurno") long id) {
		return service.traerTurnoPorId(id);
	}
	
	@GetMapping("/persona/{idPersona}")
	@ApiOperation(value = "Mostrar un Turno por persona", notes = "Servicio para mostrar un Turno a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Turno encontrado"),
			@ApiResponse(code = 404, message = "Turno no encontrado") })
	public ResponseEntity<List<GetResTurnoVo>> traerTurnoPorPersona(@PathVariable("idPersona") long id, @RequestParam String fecha) {
		return new ResponseEntity<>(service.traerGetResTurnoVoPorPersona(id, fecha), HttpStatus.OK);
	}
	
	@GetMapping("/emprendimiento/{idEmprendimiento}")
	@ApiOperation(value = "Mostrar un Turno por emprendimiento", notes = "Servicio para mostrar un Turno a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Turno encontrado"),
			@ApiResponse(code = 404, message = "Turno no encontrado") })
	public ResponseEntity<List<GetResTurnoVo>> traerTurnoPorEmp(@PathVariable("idEmprendimiento") long id, @RequestParam String fecha) {
		return new ResponseEntity<>(service.traerGetResTurnoVoPorEmprendimiento(id, fecha), HttpStatus.OK);
	}


	@PostMapping
	@ApiOperation(value = "Crear un Turno", notes = "Servicio para crear un Turno")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Turno creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Turno") })
	public ResponseEntity<Turno> crearTurno(@RequestBody TurnoVo turnoVo) {
		Turno turno = service.crearTurno(turnoVo);

		return new ResponseEntity<>(turno, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idTurno}")
	@ApiOperation(value = "Eliminar un Turno por ID", notes = "Servicio para eliminar un Turno a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Turno eliminado con exito"),
			@ApiResponse(code = 404, message = "Turno no encontrado") })
	public ResponseEntity<Empty> eliminarTurno(@PathVariable("idTurno") long id) {

		service.borrarTurno(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idTurno}")
	@ApiOperation(value = "Modificar un Turno por ID", notes = "Servicio para modificar un Turno a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Turno modificado exitosamente"),
			@ApiResponse(code = 404, message = "Turno no encontrado") })
	public ResponseEntity<Turno> updateTurno(@PathVariable("idTurno") Long id, @RequestBody TurnoVo turnoVo) {

		return new ResponseEntity<>(service.actualizarTurno(id, turnoVo), HttpStatus.OK);
	}
	
	@PatchMapping("/{idTurno}")
	@ApiOperation(value = "Modificar un Turno por ID", notes = "Servicio para modificar un Turno a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Turno modificado exitosamente"),
			@ApiResponse(code = 404, message = "Turno no encontrado") })
	public ResponseEntity<Turno> patchTurno(@PathVariable("idTurno") Long id, @RequestBody ReqPatchTurnoVo patchTurno) {

		return new ResponseEntity<>(service.patchTurno(id, patchTurno), HttpStatus.OK);
	}

}
