package com.unla.reactivar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.Persona;
import com.unla.reactivar.services.PersonaService;
import com.unla.reactivar.vo.CoordenadasVo;
import com.unla.reactivar.vo.Empty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/persona")
@Api(tags = "Persona")
public class PersonaController {

	@Autowired
	private PersonaService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Personas", notes = "Servicio para listar todas las Personas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Personas encontradas"),
			@ApiResponse(code = 404, message = "Personas no encontradas") })
	public List<Persona> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idPersona}")
	@ApiOperation(value = "Mostrar una Persona por ID", notes = "Servicio para mostrar una Persona")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Persona encontrada"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public Persona traerPersona(@PathVariable("idPersona") long id) {
		return service.traerPersonaPorId(id);
	}

	@GetMapping("/{idPersona}/coordenadas")
	@ApiOperation(value = "Mostrar coordenadas de una Persona por ID", notes = "Service para mostrar coordenadas de una Persona a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Persona encontrada"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public CoordenadasVo traerCoordenadas(@PathVariable("idPersona") long id) {
		return service.traerCoordenadas(id);
	}

	@DeleteMapping("/{idPersona}")
	@ApiOperation(value = "Eliminar una Persona por ID", notes = "Servicio para eliminar una Persona a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Persona eliminada con exito"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public ResponseEntity<Empty> eliminarPersona(@PathVariable("idPersona") long id) {

		service.borrarPersona(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

}
