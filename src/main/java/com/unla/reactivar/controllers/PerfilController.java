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
	@ApiOperation(value = "Listar todos los perfils", notes = "Service para listar todos los perfils")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perfils encontrados"),
			@ApiResponse(code = 404, message = "Perfils no encontrados") })
	public List<Perfil> traerTodosPerfiles() {
		return service.traerTodosPerfiles();
	}
	
	@GetMapping("/{idPerfil}")
	@ApiOperation(value = "Mostrar un perfil", notes = "Service para mostrar un perfil")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perfil encontrado"),
			@ApiResponse(code = 404, message = "Perfil no encontrado") })
	public Perfil traerPerfil(@PathVariable ("idPerfil") long id) {
		return service.traerPerfilPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Perfil", notes = "Servicio creador de perfils")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perfil successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Perfil> crearPerfil(@RequestBody PerfilVo perfilVo){
		Perfil perfil = service.crearPerfil(perfilVo);
		
		return new ResponseEntity<>(perfil, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idPerfil}")
	@ApiOperation(value = "Eliminar perfil", notes = "Servicio elimina Perfil")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perfil eliminado con exito"),
			@ApiResponse(code = 404, message = "Perfil no encontrado") })
	public ResponseEntity<Empty> eliminarPerfil(@PathVariable("idPerfil") long id ) {
		
		service.borrarPerfil(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idPerfil}")
	@ApiOperation(value = "Update Perfil", notes = "Perfil updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Perfil successfully updated"),
			@ApiResponse(code = 404, message = "Perfil not found") })
	public ResponseEntity<Perfil> updatePerfil(@PathVariable("idPerfil") Long id, PerfilVo perfilVo) {

		return new ResponseEntity<>(service.actualizarPerfil(id, perfilVo), HttpStatus.OK);
	}

}
