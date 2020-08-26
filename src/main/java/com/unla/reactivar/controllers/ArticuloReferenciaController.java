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

import com.unla.reactivar.models.ArticuloReferencia;
import com.unla.reactivar.services.ArticuloReferenciaService;
import com.unla.reactivar.vo.ArticuloReferenciaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/articuloReferencia")
@Api(tags = "ArticuloReferencia")
public class ArticuloReferenciaController {
	
	@Autowired
	private ArticuloReferenciaService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los articuloReferencias", notes = "Service para listar todos los articuloReferencias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ArticuloReferencias encontrados"),
			@ApiResponse(code = 404, message = "ArticuloReferencias no encontrados") })
	public List<ArticuloReferencia> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idArticuloReferencia}")
	@ApiOperation(value = "Mostrar un articuloReferencia", notes = "Service para mostrar un articuloReferencia")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ArticuloReferencia encontrado"),
			@ApiResponse(code = 404, message = "ArticuloReferencia no encontrado") })
	public ArticuloReferencia traerArticuloReferencia(@PathVariable ("idArticuloReferencia") long id) {
		return service.traerArticuloReferenciaPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear ArticuloReferencia", notes = "Servicio creador de articuloReferencias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ArticuloReferencia successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<ArticuloReferencia> crearArticuloReferencia(@RequestBody ArticuloReferenciaVo articuloReferenciaVo){
		ArticuloReferencia articuloReferencia = service.crearArticuloReferencia(articuloReferenciaVo);
		
		return new ResponseEntity<>(articuloReferencia, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idArticuloReferencia}")
	@ApiOperation(value = "Eliminar articuloReferencia", notes = "Servicio elimina ArticuloReferencia")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ArticuloReferencia eliminado con exito"),
			@ApiResponse(code = 404, message = "ArticuloReferencia no encontrado") })
	public void eliminarArticuloReferencia(@PathVariable("idArticuloReferencia") long id ) {
		
		service.borrarArticuloReferencia(id);
	}
	
	@PutMapping("/{idArticuloReferencia}")
	@ApiOperation(value = "Update ArticuloReferencia", notes = "ArticuloReferencia updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ArticuloReferencia successfully updated"),
			@ApiResponse(code = 404, message = "ArticuloReferencia not found") })
	public ResponseEntity<ArticuloReferencia> updateArticuloReferencia(@PathVariable("idArticuloReferencia") Long id, ArticuloReferenciaVo articuloReferenciaVo) {

		return new ResponseEntity<>(service.actualizarArticuloReferencia(id, articuloReferenciaVo), HttpStatus.OK);
	}

}
