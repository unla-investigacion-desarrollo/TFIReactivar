package com.unla.reactivar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.EstadoPersona;
import com.unla.reactivar.services.EstadoPersonaService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.EstadoPersonaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/estadoPersona")
@Api(tags = "EstadoPersona")
@CrossOrigin(origins = "*")
public class EstadoPersonaController {

	@Autowired
	private EstadoPersonaService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los estadoPersonas", notes = "Service para listar todos los estadoPersonas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoPersonas encontrados"),
			@ApiResponse(code = 404, message = "EstadoPersonas no encontrados") })
	public List<EstadoPersona> traerTodosEstadosPersona() {
		return service.traerTodosEstadosPersona();
	}

	@GetMapping("/{idEstadoPersona}")
	@ApiOperation(value = "Mostrar un estadoPersona", notes = "Service para mostrar un estadoPersona")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoPersona encontrado"),
			@ApiResponse(code = 404, message = "EstadoPersona no encontrado") })
	public EstadoPersona traerEstadoPersona(@PathVariable("idEstadoPersona") long id) {
		return service.traerEstadoPersonaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear EstadoPersona", notes = "Servicio creador de estadoPersonas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoPersona successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<EstadoPersona> crearEstadoPersona(@RequestBody EstadoPersonaVo estadoPersonaVo) {
		EstadoPersona estadoPersona = service.crearEstadoPersona(estadoPersonaVo);

		return new ResponseEntity<>(estadoPersona, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idEstadoPersona}")
	@ApiOperation(value = "Eliminar estadoPersona", notes = "Servicio elimina EstadoPersona")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoPersona eliminado con exito"),
			@ApiResponse(code = 404, message = "EstadoPersona no encontrado") })
	public ResponseEntity<Empty> eliminarEstadoPersona(@PathVariable("idEstadoPersona") long id) {

		service.borrarEstadoPersona(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idEstadoPersona}")
	@ApiOperation(value = "Update EstadoPersona", notes = "EstadoPersona updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "EstadoPersona successfully updated"),
			@ApiResponse(code = 404, message = "EstadoPersona not found") })
	public ResponseEntity<EstadoPersona> updateEstadoPersona(@PathVariable("idEstadoPersona") Long id,
			@RequestBody EstadoPersonaVo estadoPersonaVo) {

		return new ResponseEntity<>(service.actualizarEstadoPersona(id, estadoPersonaVo), HttpStatus.OK);
	}

}
