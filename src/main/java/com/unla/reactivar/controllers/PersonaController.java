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

import com.unla.reactivar.models.Empty;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.services.PersonaService;
import com.unla.reactivar.vo.CoordenadasVo;

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
	@ApiOperation(value = "Listar todos los personas", notes = "Service para listar todos los personas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Personas encontrados"),
			@ApiResponse(code = 404, message = "Personas no encontrados") })
	public List<Persona> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idPersona}")
	@ApiOperation(value = "Mostrar un persona", notes = "Service para mostrar un persona")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Persona encontrado"),
			@ApiResponse(code = 404, message = "Persona no encontrado") })
	public Persona traerPersona(@PathVariable ("idPersona") long id) {
		return service.traerPersonaPorId(id);
	}
	
	@GetMapping("/{idPersona}/coordenadas")
	@ApiOperation(value = "Mostrar coordenadas de una persona", notes = "Service para mostrar coordenadas de una persona")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Persona encontrada"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public CoordenadasVo traerCoordenadas(@PathVariable ("idPersona") long id) {
		return service.traerCoordenadas(id);
	}
	
	@DeleteMapping("/{idPersona}")
	@ApiOperation(value = "Eliminar persona", notes = "Servicio elimina Persona")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Persona eliminado con exito"),
			@ApiResponse(code = 404, message = "Persona no encontrado") })
	public ResponseEntity<Empty> eliminarPersona(@PathVariable("idPersona") long id ) {
		
		service.borrarPersona(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}


}
