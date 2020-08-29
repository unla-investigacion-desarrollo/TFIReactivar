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

import com.unla.reactivar.models.Articulo;
import com.unla.reactivar.models.Empty;
import com.unla.reactivar.services.ArticuloService;
import com.unla.reactivar.vo.ArticuloVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/articulo")
@Api(tags = "Articulo")
public class ArticuloController {
	
	@Autowired
	private ArticuloService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los articulos", notes = "Service para listar todos los articulos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulos encontrados"),
			@ApiResponse(code = 404, message = "Articulos no encontrados") })
	public List<Articulo> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idArticulo}")
	@ApiOperation(value = "Mostrar un articulo", notes = "Service para mostrar un articulo")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulo encontrado"),
			@ApiResponse(code = 404, message = "Articulo no encontrado") })
	public Articulo traerArticulo(@PathVariable ("idArticulo") long id) {
		return service.traerArticuloPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Articulo", notes = "Servicio creador de articulos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulo successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Articulo> crearArticulo(@RequestBody ArticuloVo articuloVo){
		Articulo articulo = service.crearArticulo(articuloVo);
		
		return new ResponseEntity<>(articulo, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idArticulo}")
	@ApiOperation(value = "Eliminar articulo", notes = "Servicio elimina Articulo")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulo eliminado con exito"),
			@ApiResponse(code = 404, message = "Articulo no encontrado") })
	public ResponseEntity<Empty> eliminarArticulo(@PathVariable("idArticulo") long id ) {
		
		service.borrarArticulo(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idArticulo}")
	@ApiOperation(value = "Update Articulo", notes = "Articulo updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Articulo successfully updated"),
			@ApiResponse(code = 404, message = "Articulo not found") })
	public ResponseEntity<Articulo> updateArticulo(@PathVariable("idArticulo") Long id, ArticuloVo articuloVo) {

		return new ResponseEntity<>(service.actualizarArticulo(id, articuloVo), HttpStatus.OK);
	}

}
