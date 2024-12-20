package com.unla.reactivar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.unla.reactivar.vo.ReqPutPersonaJuridicaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/juridica")
@Api(tags = "PersonaJuridica")
@CrossOrigin(origins = "*")
public class PersonaJuridicaController {

	@Autowired
	private PersonaJuridicaService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Personas Juridicas", notes = "Servicio para listar todas las Personas Juridicas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Personas Juridicas encontradas"),
			@ApiResponse(code = 404, message = "Personas Juridicas no encontradas") })
	public List<PersonaJuridica> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/inactivos")
	@ApiOperation(value = "Listar todas las Personas Juridicas inactivas", notes = "Servicio para listar todas las Personas Juridicas inactivas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Personas Juridicas inactivas encontradas"),
			@ApiResponse(code = 404, message = "Personas Juridicas no encontradas") })
	public List<PersonaJuridica> traerTodosInactivos() {
		return service.traerTodosInactivos();
	}

	@GetMapping("/porEstado/{idEstadoPersona}")
	@ApiOperation(value = "Listar todas las Personas Juridicas a partir de un Estado", notes = "Servicio para listar todas las Personas Juridicas a partir de un Estado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Personas Juridicas por estado encontradas"),
			@ApiResponse(code = 404, message = "Personas Juridicas no encontradas") })
	public List<PersonaJuridica> traerPersonasPorEstado(@PathVariable("idEstadoPersona") long id) {
		return service.traerPersonasPorEstado(id);
	}

	@GetMapping("/{idPersonaJuridica}")
	@ApiOperation(value = "Mostrar una Persona Juridica por ID", notes = "Servicio para mostrar una Persona Juridica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Juridica encontrada"),
			@ApiResponse(code = 404, message = "Persona Juridica no encontrada") })
	public PersonaJuridica traerPersonaJuridica(@PathVariable("idPersonaJuridica") long id) {
		return service.traerPersonaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una Persona Juridica", notes = "Servicio para crear Persona Juridica")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Persona Juridica creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Persona Juridica") })
	public ResponseEntity<PersonaJuridica> crearPersonaJuridica(@RequestBody PersonaJuridicaVo personaJuridicaVo) {
		PersonaJuridica personaJuridica = service.crearPersona(personaJuridicaVo);

		return new ResponseEntity<>(personaJuridica, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idPersonaJuridica}")
	@ApiOperation(value = "Eliminar uan Persona Juridica por ID", notes = "Servicio para eliminar una Persona Juridica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Juridica eliminada con exito"),
			@ApiResponse(code = 404, message = "Persona Juridica no encontrada") })
	public ResponseEntity<Empty> eliminarPersonaJuridica(@PathVariable("idPersonaJuridica") long id) {

		service.borrarPersona(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idPersonaJuridica}")
	@ApiOperation(value = "Modificar una Persona Juridica por ID", notes = "Servicio para modificar una Persona Juridica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Juridica modificada exitosamente"),
			@ApiResponse(code = 404, message = "Persona Juridica no encontrada") })
	public ResponseEntity<PersonaJuridica> updatePersonaJuridica(@PathVariable("idPersonaJuridica") Long id,
			@RequestBody ReqPutPersonaJuridicaVo personaJuridicaVo) {

		return new ResponseEntity<>(service.actualizarPersonaJuridica(id, personaJuridicaVo), HttpStatus.OK);
	}

	@PatchMapping("/{idPersonaJuridica}")
	@ApiOperation(value = "Activar una Persona Juridica por ID", notes = "Servicio para Activar una Persona Juridica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Juridica activada exitosamente"),
			@ApiResponse(code = 404, message = "Persona Juridica no encontrada") })
	public ResponseEntity<PersonaJuridica> activarPersonaJuridica(@PathVariable("idPersonaJuridica") Long id) {

		return new ResponseEntity<>(service.activarPersonaJuridica(id), HttpStatus.OK);
	}

	@PatchMapping("bajaLogica/{idPersonaJuridica}")
	@ApiOperation(value = "Baja logica Persona Juridica por ID", notes = "Servicio para dar de Baja a una Persona Juridica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Juridica dada de baja exitosamente"),
			@ApiResponse(code = 404, message = "Persona Juridica no encontrada") })
	public ResponseEntity<PersonaJuridica> bajaLogicaPersonaJuridica(@PathVariable("idPersonaJuridica") Long id) {

		return new ResponseEntity<>(service.bajaLogicaPersonaJuridica(id), HttpStatus.OK);
	}

	@PatchMapping("desactivar/{idPersonaJuridica}")
	@ApiOperation(value = "Desactivar una Persona Juridica por ID", notes = "Servicio para Desactivar una Persona Juridica a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona Juridica desactivada exitosamente"),
			@ApiResponse(code = 404, message = "Persona Juridica no encontrada") })
	public ResponseEntity<PersonaJuridica> desactivarPersonaJuridica(@PathVariable("idPersonaJuridica") Long id) {

		return new ResponseEntity<>(service.desactivarPersonaJuridica(id), HttpStatus.OK);
	}

}
