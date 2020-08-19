package com.unla.alimentar.controllers;

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

import com.unla.alimentar.models.EstadoTurno;
import com.unla.alimentar.services.EstadoTurnoService;
import com.unla.alimentar.vo.EstadoTurnoVo;

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
	@ApiOperation(value = "Listar todos los estadoTurnos", notes = "Service para listar todos los estadoTurnos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoTurnos encontrados"),
			@ApiResponse(code = 404, message = "EstadoTurnos no encontrados") })
	public List<EstadoTurno> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idEstadoTurno}")
	@ApiOperation(value = "Mostrar un estadoTurno", notes = "Service para mostrar un estadoTurno")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoTurno encontrado"),
			@ApiResponse(code = 404, message = "EstadoTurno no encontrado") })
	public EstadoTurno traerEstadoTurno(@PathVariable ("idEstadoTurno") long id) {
		return service.traerEstadoTurnoPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear EstadoTurno", notes = "Servicio creador de estadoTurnos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoTurno successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<EstadoTurno> crearEstadoTurno(@RequestBody EstadoTurnoVo estadoTurnoVo){
		EstadoTurno estadoTurno = service.crearEstadoTurno(estadoTurnoVo);
		
		return new ResponseEntity<>(estadoTurno, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idEstadoTurno}")
	@ApiOperation(value = "Eliminar estadoTurno", notes = "Servicio elimina EstadoTurno")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoTurno eliminado con exito"),
			@ApiResponse(code = 404, message = "EstadoTurno no encontrado") })
	public void eliminarEstadoTurno(@PathVariable("idEstadoTurno") long id ) {
		
		service.borrarEstadoTurno(id);
	}
	
	@PutMapping("/{idEstadoTurno}")
	@ApiOperation(value = "Update EstadoTurno", notes = "EstadoTurno updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "EstadoTurno successfully updated"),
			@ApiResponse(code = 404, message = "EstadoTurno not found") })
	public ResponseEntity<EstadoTurno> updateEstadoTurno(@PathVariable("idEstadoTurno") Long id, EstadoTurnoVo estadoTurnoVo) {

		return new ResponseEntity<>(service.actualizarEstadoTurno(id, estadoTurnoVo), HttpStatus.OK);
	}

}
