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

import com.unla.reactivar.models.Endpoint;

import com.unla.reactivar.services.EndpointService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.EndpointVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/endpoint")
@Api(tags = "Endpoint")
public class EndpointController {

	@Autowired
	private EndpointService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Endpoint", notes = "Servicio para listar todas los Endpoint")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Endpoint encontrados"),
			@ApiResponse(code = 404, message = "Endpoint no encontrados") })
	public List<Endpoint> traerTodosEndpoints() {
		return service.traerTodosEndpoints();

	}

	@GetMapping("/{idEndpoint}")
	@ApiOperation(value = "Mostrar un Endpoint por ID", notes = "Servicio para mostrar un Endpoint a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Endpoint encontrado"),
			@ApiResponse(code = 404, message = "Endpoint no encontrado") })
	public Endpoint traerEndpoint(@PathVariable("idEndpoint") long id) {
		return service.traerEndpointPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Endpoint", notes = "Servicio para crear un Endpoint")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Endpoint creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Endpoint") })
	public ResponseEntity<Endpoint> crearEndpoint(@RequestBody EndpointVo endpointVo) {
		Endpoint endpoint = service.crearEndpoint(endpointVo);

		return new ResponseEntity<>(endpoint, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idEndpoint}")
	@ApiOperation(value = "Eliminar un Endpoint por ID", notes = "Servicio para eliminar un Endpoint a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Endpoint eliminado con exito"),
			@ApiResponse(code = 404, message = "Endpoint no encontrado") })
	public ResponseEntity<Empty> eliminarEndpoint(@PathVariable("idEndpoint") long id) {

		service.borrarEndpoint(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idEndpoint}")
	@ApiOperation(value = "Modificar un Endpoint por ID", notes = "Servicio para modificar un Endpoint a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Endpoint modificado con exito"),
			@ApiResponse(code = 404, message = "Endpoint no encontrado") })
	public ResponseEntity<Endpoint> updateEndpoint(@PathVariable("idEndpoint") Long id, EndpointVo endpointVo) {

		return new ResponseEntity<>(service.actualizarEndpoint(id, endpointVo), HttpStatus.OK);
	}

}
