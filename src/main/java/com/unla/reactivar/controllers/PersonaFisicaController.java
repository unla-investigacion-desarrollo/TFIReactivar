package com.unla.reactivar.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.PersonaFisica;
import com.unla.reactivar.services.PersonaFisicaService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.PersonaFisicaVo;
import com.unla.reactivar.vo.ReclamoVo;
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
	@ApiOperation(value = "Listar todas las Personas Fisicas", notes = "Servicio para listar todas las Personas Fisicas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Personas Fisicas encontradas"),
			@ApiResponse(code = 404, message = "Personas Fisicas no encontradas") })
	public List<PersonaFisica> traerTodos() {
		return service.traerTodasPersonasFisicas();
	}

	@GetMapping("/porEstado/{idEstadoPersona}")
	@ApiOperation(value = "Listar todas las Personas Fisicas a partir de un Estado", notes = "Servicio para listar todas las Personas Fisicas a partir de un Estado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Personas Fisicas por estado encontradas"),
			@ApiResponse(code = 404, message = "Personas Fisicas no encontradas") })
	public List<PersonaFisica> traerPersonasPorEstado(@PathVariable("idEstadoPersona") long id) {
		return service.traerPersonasPorEstado(id);
	}

	@GetMapping("/{idPersonaFisica}")
	@ApiOperation(value = "Mostrar una Persona Fisica por ID", notes = "Servicio para mostrar una Persona Fisica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Fisica encontrada"),
			@ApiResponse(code = 404, message = "Persona Fisica no encontrada") })
	public PersonaFisica traerPersonaFisica(@PathVariable("idPersonaFisica") long id) {
		return service.traerPersonaFisicaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una PersonaFisica", notes = "Servicio para crear una Persona Fisica")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Fisica creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Persona Fisica") })
	public ResponseEntity<PersonaFisica> crearPersonaFisica(@RequestBody PersonaFisicaVo personaFisicaVo) {
		PersonaFisica personaFisica = service.crearPersonaFisica(personaFisicaVo);

		return new ResponseEntity<>(personaFisica, HttpStatus.CREATED);
	}

	@PatchMapping("/{idPersonaFisica}")
	@ApiOperation(value = "Activar una Persona Fisica por ID", notes = "Servicio para Activar una Persona Fisica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Fisica activada exitosamente"),
			@ApiResponse(code = 404, message = "Persona Fisica no encontrada") })
	public ResponseEntity<PersonaFisica> activarPersonaFisica(@PathVariable("idPersonaFisica") Long id) {

		return new ResponseEntity<>(service.activarPersonaFisica(id), HttpStatus.OK);
	}

	@PatchMapping("desactivar/{idPersonaFisica}")
	@ApiOperation(value = "Desactivar una Persona Fisica por ID", notes = "Servicio para Desactivar una Persona Fisica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Fisica desactivada exitosamente"),
			@ApiResponse(code = 404, message = "Persona Fisica no encontrada") })
	public ResponseEntity<PersonaFisica> desactivarPersonaFisica(@PathVariable("idPersonaFisica") Long id) {

		return new ResponseEntity<>(service.desactivarPersonaFisica(id), HttpStatus.OK);
	}

	@DeleteMapping("/{idPersonaFisica}")
	@ApiOperation(value = "Eliminar una Persona Fisica por ID", notes = "Servicio para eliminar una Persona Fisica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Fisica eliminada con exito"),
			@ApiResponse(code = 404, message = "Persona Fisica no encontrada") })
	public ResponseEntity<Empty> eliminarPersonaFisica(@PathVariable("idPersonaFisica") long id) {

		service.borrarPersonaFisica(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idPersonaFisica}")
	@ApiOperation(value = "Modificar una Persona Fisica por ID", notes = "Modificar una Persona Fisica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Fisica modificada exitosamente"),
			@ApiResponse(code = 404, message = "Persona Fisica no encontrada") })
	public ResponseEntity<Persona> updatePersonaFisica(@PathVariable("idPersonaFisica") Long id,
			@RequestBody ReqPutPersonaFisicaVo personaFisicaVo) {

		return new ResponseEntity<>(service.actualizarPersonaFisica(id, personaFisicaVo), HttpStatus.OK);
	}

	@PatchMapping("bajaLogica/{idPersonaFisica}")
	@ApiOperation(value = "Baja logica Persona Fisica por ID", notes = "Servicio para dar de Baja a una Persona Fisica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Fisica dada de baja exitosamente"),
			@ApiResponse(code = 404, message = "Persona Fisica no encontrada") })
	public ResponseEntity<PersonaFisica> bajaLogicaPersonaFisica(@PathVariable("idPersonaFisica") Long id) {

		return new ResponseEntity<>(service.bajaLogicaPersonaFisica(id), HttpStatus.OK);
	}
	
	@PostMapping("/reclamo")
	public ResponseEntity<Empty> crearReclamo(@RequestBody ReclamoVo reclamo) {
		service.enviarReclamo(reclamo);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

}
