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

import com.unla.reactivar.models.ReqArticulo;
import com.unla.reactivar.services.ArticuloService;
import com.unla.reactivar.vo.ArticuloVo;
import com.unla.reactivar.vo.Empty;

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
	@ApiOperation(value = "Listar todos los articulos", notes = "Servicio para listar todos los articulos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Articulos encontrados"),
			@ApiResponse(code = 404, message = "Articulos no encontrados") })
	public List<ReqArticulo> traerTodosArticulos() {
		return service.traerTodosArticulos();
	}

	@GetMapping("/{idArticulo}")
	@ApiOperation(value = "Traer un articulo por ID", notes = "Servicio para mostrar un articulo a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Articulo encontrado"),
			@ApiResponse(code = 404, message = "Articulo no encontrado") })
	public ReqArticulo traerArticulo(@PathVariable("idArticulo") long id) {
		return service.traerArticuloPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Articulo", notes = "Servicio creador de articulos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulo exitosamente creado"),
			@ApiResponse(code = 400, message = "No se pudo crear articulo") })
	public ResponseEntity<ReqArticulo> crearArticulo(@RequestBody ArticuloVo articuloVo) {
		ReqArticulo articulo = service.crearArticulo(articuloVo);

		return new ResponseEntity<>(articulo, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idArticulo}")
	@ApiOperation(value = "Eliminar un articulo por ID", notes = "Servicio para eliminar Articulos a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Articulo eliminado con exito"),
			@ApiResponse(code = 404, message = "Articulo no encontrado") })
	public ResponseEntity<Empty> eliminarArticulo(@PathVariable("idArticulo") long id) {

		service.borrarArticulo(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idArticulo}")
	@ApiOperation(value = "Modificar un Articulo por ID", notes = "Servicio para modificar articulos a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Articulo modificado con exito"),
			@ApiResponse(code = 404, message = "Articulo no encontrado") })
	public ResponseEntity<ReqArticulo> updateArticulo(@PathVariable("idArticulo") Long id, ArticuloVo articuloVo) {

		return new ResponseEntity<>(service.actualizarArticulo(id, articuloVo), HttpStatus.OK);
	}

}
