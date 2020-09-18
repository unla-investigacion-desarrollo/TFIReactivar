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

import com.unla.reactivar.models.Perfil;
import com.unla.reactivar.services.PerfilService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.PerfilVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/perfil")
@Api(tags = "Perfil")
public class PerfilController {

	@Autowired
	private PerfilService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Perfiles", notes = "Servicio para listar todos los Perfiles")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perfiles encontrados"),
			@ApiResponse(code = 404, message = "Perfils no encontrados") })
	public List<Perfil> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idPerfil}")
	@ApiOperation(value = "Mostrar un Perfil por ID", notes = "Servicio para mostrar un Perfil a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perfil encontrado"),
			@ApiResponse(code = 404, message = "Perfil no encontrado") })
	public Perfil traerPerfil(@PathVariable("idPerfil") long id) {
		return service.traerPerfilPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Perfil", notes = "Servicio para crear un Perfil")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perfil creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear un Perfil") })
	public ResponseEntity<Perfil> crearPerfil(@RequestBody PerfilVo perfilVo) {
		Perfil perfil = service.crearPerfil(perfilVo);

		return new ResponseEntity<>(perfil, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idPerfil}")
	@ApiOperation(value = "Eliminar un Perfil por ID", notes = "Servicio para eliminar un Perfil a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perfil eliminado exitosamente"),
			@ApiResponse(code = 404, message = "Perfil no encontrado") })
	public ResponseEntity<Empty> eliminarPerfil(@PathVariable("idPerfil") long id) {

		service.borrarPerfil(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idPerfil}")
	@ApiOperation(value = "Modificar un Perfil por ID", notes = "Servicio para modificar un Perfil a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Perfil modificado exitosamente"),
			@ApiResponse(code = 404, message = "Perfil no encontrado") })
	public ResponseEntity<Perfil> updatePerfil(@PathVariable("idPerfil") Long id, PerfilVo perfilVo) {

		return new ResponseEntity<>(service.actualizarPerfil(id, perfilVo), HttpStatus.OK);
	}

}
