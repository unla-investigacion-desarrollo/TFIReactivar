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

import com.unla.reactivar.models.PersonaJuridica;
import com.unla.reactivar.services.PersonaJuridicaService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.PersonaJuridicaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/juridica")
@Api(tags = "PersonaJuridica")
public class PersonaJuridicaController {
	
	@Autowired
	private PersonaJuridicaService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los personaJuridicas", notes = "Service para listar todos los personaJuridicas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "PersonaJuridicas encontrados"),
			@ApiResponse(code = 404, message = "PersonaJuridicas no encontrados") })
	public List<PersonaJuridica> traerTodasPersonasJuridicas() {
		return service.traerTodasPersonasJuridicas();
	}
	
	@GetMapping("/{idPersonaJuridica}")
	@ApiOperation(value = "Mostrar un personaJuridica", notes = "Service para mostrar un personaJuridica")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "PersonaJuridica encontrado"),
			@ApiResponse(code = 404, message = "PersonaJuridica no encontrado") })
	public PersonaJuridica traerPersonaJuridica(@PathVariable ("idPersonaJuridica") long id) {
		return service.traerPersonaPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear PersonaJuridica", notes = "Servicio creador de personaJuridicas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "PersonaJuridica successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<PersonaJuridica> crearPersonaJuridica(@RequestBody PersonaJuridicaVo personaJuridicaVo){
		PersonaJuridica personaJuridica = service.crearPersona(personaJuridicaVo);
		
		return new ResponseEntity<>(personaJuridica, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idPersonaJuridica}")
	@ApiOperation(value = "Eliminar personaJuridica", notes = "Servicio elimina PersonaJuridica")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "PersonaJuridica eliminado con exito"),
			@ApiResponse(code = 404, message = "PersonaJuridica no encontrado") })
	public ResponseEntity<Empty> eliminarPersonaJuridica(@PathVariable("idPersonaJuridica") long id ) {
		
		service.borrarPersona(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idPersonaJuridica}")
	@ApiOperation(value = "Update PersonaJuridica", notes = "PersonaJuridica updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "PersonaJuridica successfully updated"),
			@ApiResponse(code = 404, message = "PersonaJuridica not found") })
	public ResponseEntity<PersonaJuridica> updatePersonaJuridica(@PathVariable("idPersonaJuridica") Long id, PersonaJuridicaVo personaJuridicaVo) {

		return new ResponseEntity<>(service.actualizarPersonaJuridica(id, personaJuridicaVo), HttpStatus.OK);
	}

}
