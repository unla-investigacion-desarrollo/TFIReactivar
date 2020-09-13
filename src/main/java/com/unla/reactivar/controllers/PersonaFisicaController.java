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

import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.PersonaFisica;
import com.unla.reactivar.services.PersonaFisicaService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.PersonaFisicaVo;
import com.unla.reactivar.vo.ReqPutPersonaFisicaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/fisica")
@Api(tags = "PersonaFisica")
public class PersonaFisicaController {

	@Autowired
	private PersonaFisicaService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los personaFisicas", notes = "Service para listar todos los personaFisicas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "PersonaFisicas encontrados"),
			@ApiResponse(code = 404, message = "PersonaFisicas no encontrados") })
	public List<PersonaFisica> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idPersonaFisica}")
	@ApiOperation(value = "Mostrar un personaFisica", notes = "Service para mostrar un personaFisica")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "PersonaFisica encontrado"),
			@ApiResponse(code = 404, message = "PersonaFisica no encontrado") })
	public Persona traerPersonaFisica(@PathVariable ("idPersonaFisica") long id) {
		return service.traerPersonaFisicaPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear PersonaFisica", notes = "Servicio creador de personaFisicas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "PersonaFisica successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Persona> crearPersonaFisica(@RequestBody PersonaFisicaVo personaFisicaVo){
		Persona personaFisica = service.crearPersonaFisica(personaFisicaVo);
		
		return new ResponseEntity<>(personaFisica, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idPersonaFisica}")
	@ApiOperation(value = "Eliminar personaFisica", notes = "Servicio elimina PersonaFisica")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "PersonaFisica eliminado con exito"),
			@ApiResponse(code = 404, message = "PersonaFisica no encontrado") })
	public ResponseEntity<Empty> eliminarPersonaFisica(@PathVariable("idPersonaFisica") long id ) {
		
		service.borrarPersonaFisica(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idPersonaFisica}")
	@ApiOperation(value = "Update PersonaFisica", notes = "PersonaFisica updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "PersonaFisica successfully updated"),
			@ApiResponse(code = 404, message = "PersonaFisica not found") })
	public ResponseEntity<Persona> updatePersonaFisica(@PathVariable("idPersonaFisica") Long id, ReqPutPersonaFisicaVo personaFisicaVo) {

		return new ResponseEntity<>(service.actualizarPersonaFisica(id, personaFisicaVo), HttpStatus.OK);
	}
	
}
