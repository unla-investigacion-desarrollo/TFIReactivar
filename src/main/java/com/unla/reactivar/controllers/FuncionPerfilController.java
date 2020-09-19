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

import com.unla.reactivar.models.FuncionPerfil;
import com.unla.reactivar.services.FuncionPerfilService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.FuncionPerfilVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/funcionPerfil")
@Api(tags = "FuncionPerfil")
public class FuncionPerfilController {

	@Autowired
	private FuncionPerfilService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Funciones de Perfil", notes = "Servicio para listar todas las Funciones de Perfil")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Funciones de Perfil encontradas"),
			@ApiResponse(code = 404, message = "Funciones de Perfil no encontradas") })
	public List<FuncionPerfil> traerTodos() {
		return service.traerTodasFuncionesPerfil();
	}

	@GetMapping("/{idFuncionPerfil}")
	@ApiOperation(value = "Mostrar una Función de Perfil por ID", notes = "Servicio para mostrar una Función de Perfil a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Función de Perfil encontrada"),
			@ApiResponse(code = 404, message = "Función de Perfil no encontrada") })
	public FuncionPerfil traerFuncionPerfil(@PathVariable("idFuncionPerfil") long id) {
		return service.traerFuncionPerfilPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una Función de Perfil", notes = "Servicio para crear una Función de Perfil")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Función de Perfil creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear una Función de Perfil") })
	public ResponseEntity<FuncionPerfil> crearFuncionPerfil(@RequestBody FuncionPerfilVo funcionPerfilVo) {
		FuncionPerfil funcionPerfil = service.crearFuncionPerfil(funcionPerfilVo);

		return new ResponseEntity<>(funcionPerfil, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idFuncionPerfil}")
	@ApiOperation(value = "Eliminar una Función de Perfil por ID", notes = "Servicio para eliminar una Función de Perfil a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Función de Perfil eliminada con exito"),
			@ApiResponse(code = 404, message = "Función de Perfil no encontrada") })
	public ResponseEntity<Empty> eliminarFuncionPerfil(@PathVariable("idFuncionPerfil") long id) {

		service.borrarFuncionPerfil(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idFuncionPerfil}")
	@ApiOperation(value = "Modifiacar una Función de Perfil por ID", notes = "Servicio para modificar una Función de Perfil a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Función de Perfil modificada con exito"),
			@ApiResponse(code = 404, message = "Función de Perfil no encontrada") })
	public ResponseEntity<FuncionPerfil> updateFuncionPerfil(@PathVariable("idFuncionPerfil") Long id,
			FuncionPerfilVo funcionPerfilVo) {

		return new ResponseEntity<>(service.actualizarFuncionPerfil(id, funcionPerfilVo), HttpStatus.OK);
	}

}
