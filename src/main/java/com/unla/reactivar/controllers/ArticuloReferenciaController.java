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
import com.unla.reactivar.vo.ReqArticuloReferenciaVo;
import com.unla.reactivar.vo.Empty;

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
	@ApiOperation(value = "Listar todos los Articulo Referencia", notes = "Servicio para listar todos los Articulo Referencia")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulos Referencia encontrados"),
			@ApiResponse(code = 404, message = "Articulos Referencia no encontrados") })
	public List<ArticuloReferencia> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idArticuloReferencia}")
	@ApiOperation(value = "Mostrar un Articulo Referencia por ID", notes = "Servicio para mostrar un Articulo Referencia a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulo Referencia encontrado"),
			@ApiResponse(code = 404, message = "Articulo Referencia no encontrado") })
	public ArticuloReferencia traerArticuloReferencia(@PathVariable("idArticuloReferencia") long id) {
		return service.traerArticuloReferenciaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Articulo Referencia", notes = "Servicio creador de Articulos Referencia")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulo Referencia creado existosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Articulo Referencia") })
	public ResponseEntity<ArticuloReferencia> crearArticuloReferencia(
			@RequestBody ReqArticuloReferenciaVo articuloReferenciaVo) {
		ArticuloReferencia articuloReferencia = service.crearArticuloReferencia(articuloReferenciaVo);

		return new ResponseEntity<>(articuloReferencia, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idArticuloReferencia}")
	@ApiOperation(value = "Eliminar un Articulo Referencia por ID", notes = "Servicio para eliminar un Articulo Referencia a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Articulo Referencia eliminado con exito"),
			@ApiResponse(code = 404, message = "No se pudo eliminar Articulo Referencia") })
	public ResponseEntity<Empty> eliminarArticuloReferencia(@PathVariable("idArticuloReferencia") long id) {

		service.borrarArticuloReferencia(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idArticuloReferencia}")
	@ApiOperation(value = "Modificar Articulo Referencia por ID", notes = "Servicio para modificar Articulo Referencia a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Articulo Referencia modificado exitosamente"),
			@ApiResponse(code = 404, message = "Articulo Referencia no encontrado") })
	public ResponseEntity<ArticuloReferencia> updateArticuloReferencia(@PathVariable("idArticuloReferencia") Long id,
			ReqArticuloReferenciaVo articuloReferenciaVo) {

		return new ResponseEntity<>(service.actualizarArticuloReferencia(id, articuloReferenciaVo), HttpStatus.OK);
	}

}
