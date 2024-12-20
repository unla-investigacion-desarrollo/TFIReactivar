package com.unla.reactivar.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.Persona;
import com.unla.reactivar.services.PersonaService;
import com.unla.reactivar.vo.BusquedaPorContactoVo;
import com.unla.reactivar.vo.CoordenadasVo;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.PasswordRecoveryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/persona")
@Api(tags = "Persona")
@CrossOrigin(origins = "*")
public class PersonaController {

	@Autowired
	private PersonaService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Personas", notes = "Servicio para listar todas las Personas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Personas encontradas"),
			@ApiResponse(code = 404, message = "Personas no encontradas") })
	public List<Persona> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/porEstado/{idEstadoPersona}")
	@ApiOperation(value = "Listar todas las Personas a partir de un Estado", notes = "Servicio para listar todas las Personas a partir de un Estado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Personas por estado encontradas"),
			@ApiResponse(code = 404, message = "Personas no encontradas") })
	public List<Persona> traerPersonasPorEstado(@PathVariable("idEstadoPersona") long id) {
		return service.traerPersonasPorEstado(id);
	}

	@GetMapping("/{idPersona}")
	@ApiOperation(value = "Mostrar una Persona por ID", notes = "Servicio para mostrar una Persona")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona encontrada"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public Persona traerPersona(@PathVariable("idPersona") long id) {
		return service.traerPersonaPorId(id);
	}

	@GetMapping("/{idPersona}/coordenadas")
	@ApiOperation(value = "Mostrar coordenadas de una Persona por ID", notes = "Service para mostrar coordenadas de una Persona a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona encontrada"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public CoordenadasVo traerCoordenadas(@PathVariable("idPersona") long id) {
		return service.traerCoordenadas(id);
	}

	@DeleteMapping("/{idPersona}")
	@ApiOperation(value = "Eliminar una Persona por ID", notes = "Servicio para eliminar una Persona a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona eliminada con exito"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public ResponseEntity<Empty> eliminarPersona(@PathVariable("idPersona") long id) {

		service.borrarPersona(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<Empty> resetPassword(@RequestParam("email") String email) {

		service.recuperarContrasenia(email);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@GetMapping("/changePassword")
	public ResponseEntity<Empty> changePassword(@RequestParam("token") String token) {

		service.cambiarContrasenia(token);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PostMapping("/savePassword")
	public ResponseEntity<Persona> savePassword(@RequestBody PasswordRecoveryVo passwordRecoveryVo) {

		Persona persona = service.guardarContrasenia(passwordRecoveryVo);

		return new ResponseEntity<>(persona, HttpStatus.OK);
	}

	@GetMapping("/validateEmail")
	public ResponseEntity<Empty> validateEmail(@RequestParam("token") String token) {

		service.validarEmail(token);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@GetMapping("/resendValidationEmail")
	public ResponseEntity<Empty> resendValidationEmil(@RequestParam("email") String email) {

		service.reenviarValidarEmail(email);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PostMapping("/{idPersona}/informePorContacto")
	public ResponseEntity<Empty> informePosibleContacto(HttpServletResponse response,
			@PathVariable("idPersona") long idPersona, @RequestBody BusquedaPorContactoVo busquedaPorFechas) {

		service.generarInformePorContactoEstrecho(response, idPersona, busquedaPorFechas.getFechaInicio(),
				busquedaPorFechas.getFechaFin());

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PatchMapping("/{idPersona}/perfil")
	public ResponseEntity<Persona> modificarPerfil(HttpServletResponse response,
			@PathVariable("idPersona") long idPersona, @RequestParam long idPerfil) {
		
		Persona persona = service.modificarPerfil(idPersona, idPerfil);

		return new ResponseEntity<>(persona, HttpStatus.OK);
	}
	
	@PatchMapping("/{idPersona}/estadoPersona")
	public ResponseEntity<Persona> modificarEstadoPersona(HttpServletResponse response,
			@PathVariable("idPersona") long idPersona, @RequestParam long idEstadoPersona) {
		
		Persona persona = service.modificarEstadoPersona(idPersona, idEstadoPersona);

		return new ResponseEntity<>(persona, HttpStatus.OK);
	}

	@PatchMapping("bajaLogica/{idPersona}")
	@ApiOperation(value = "Baja logica Persona por ID", notes = "Servicio para dar de Baja a una Persona a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona dada de baja exitosamente"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public ResponseEntity<Persona> bajaLogicaPersonaJuridica(@PathVariable("idPersona") Long id) {

		return new ResponseEntity<>(service.bajaLogicaPersona(id), HttpStatus.OK);
	}

	@PatchMapping("/{idPersona}")
	@ApiOperation(value = "Activar una Persona por ID", notes = "Servicio para Activar una Persona a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona activada exitosamente"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public ResponseEntity<Persona> activarPersona(@PathVariable("idPersona") Long id) {

		return new ResponseEntity<>(service.activarPersona(id), HttpStatus.OK);
	}

	@PatchMapping("desactivar/{idPersona}")
	@ApiOperation(value = "Desactivar una Persona por ID", notes = "Servicio para Desactivar una Persona a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Persona desactivada exitosamente"),
			@ApiResponse(code = 404, message = "Persona no encontrada") })
	public ResponseEntity<Persona> desactivarPersona(@PathVariable("idPersona") Long id) {

		return new ResponseEntity<>(service.desactivarPersona(id), HttpStatus.OK);
	}

}
