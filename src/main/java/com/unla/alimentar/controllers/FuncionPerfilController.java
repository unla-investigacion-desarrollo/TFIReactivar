package com.unla.alimentar.controllers;

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

import com.unla.alimentar.models.FuncionPerfil;
import com.unla.alimentar.services.FuncionPerfilService;
import com.unla.alimentar.vo.FuncionPerfilVo;

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
	@ApiOperation(value = "Listar todos los funcionPerfils", notes = "Service para listar todos los funcionPerfils")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "FuncionPerfils encontrados"),
			@ApiResponse(code = 404, message = "FuncionPerfils no encontrados") })
	public List<FuncionPerfil> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idFuncionPerfil}")
	@ApiOperation(value = "Mostrar un funcionPerfil", notes = "Service para mostrar un funcionPerfil")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "FuncionPerfil encontrado"),
			@ApiResponse(code = 404, message = "FuncionPerfil no encontrado") })
	public FuncionPerfil traerFuncionPerfil(@PathVariable ("idFuncionPerfil") long id) {
		return service.traerFuncionPerfilPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear FuncionPerfil", notes = "Servicio creador de funcionPerfils")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "FuncionPerfil successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<FuncionPerfil> crearFuncionPerfil(@RequestBody FuncionPerfilVo funcionPerfilVo){
		FuncionPerfil funcionPerfil = service.crearFuncionPerfil(funcionPerfilVo);
		
		return new ResponseEntity<>(funcionPerfil, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idFuncionPerfil}")
	@ApiOperation(value = "Eliminar funcionPerfil", notes = "Servicio elimina FuncionPerfil")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "FuncionPerfil eliminado con exito"),
			@ApiResponse(code = 404, message = "FuncionPerfil no encontrado") })
	public void eliminarFuncionPerfil(@PathVariable("idFuncionPerfil") long id ) {
		
		service.borrarFuncionPerfil(id);
	}
	
	@PutMapping("/{idFuncionPerfil}")
	@ApiOperation(value = "Update FuncionPerfil", notes = "FuncionPerfil updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "FuncionPerfil successfully updated"),
			@ApiResponse(code = 404, message = "FuncionPerfil not found") })
	public ResponseEntity<FuncionPerfil> updateAbility(@PathVariable("idFuncionPerfil") Long id, FuncionPerfilVo funcionPerfilVo) {

		return new ResponseEntity<>(service.actualizarFuncionPerfil(id, funcionPerfilVo), HttpStatus.OK);
	}

}
