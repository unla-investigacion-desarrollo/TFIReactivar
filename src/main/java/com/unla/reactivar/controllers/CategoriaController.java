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

import com.unla.reactivar.models.Categoria;
import com.unla.reactivar.services.CategoriaService;
import com.unla.reactivar.vo.CategoriaVo;
import com.unla.reactivar.vo.Empty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/categoria")
@Api(tags = "Categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@GetMapping
	@ApiOperation(value = "Listar todos las Categorias", notes = "Servicio para listar todas las Categorias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Categorias encontrados"),
			@ApiResponse(code = 404, message = "Categorias no encontradas") })
	public List<Categoria> traerTodos() {
		return service.traerTodasCategorias();
	}

	@GetMapping("/{idCategoria}")
	@ApiOperation(value = "Mostrar un categoria por ID", notes = "Servicio para mostrar una Categoria a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Categoria encontrado"),
			@ApiResponse(code = 404, message = "Categoria no encontrada") })
	public Categoria traerCategoria(@PathVariable("idCategoria") long id) {
		return service.traerCategoriaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear Categoria", notes = "Servicio para crear Categorias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Categoria creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Categoria") })
	public ResponseEntity<Categoria> crearCategoria(@RequestBody CategoriaVo categoriaVo) {
		Categoria categoria = service.crearCategoria(categoriaVo);

		return new ResponseEntity<>(categoria, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idCategoria}")
	@ApiOperation(value = "Eliminar categoria por ID", notes = "Servicio para eliminar una Categoria a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Categoria eliminada con exito"),
			@ApiResponse(code = 404, message = "Categoria no encontrada") })
	public ResponseEntity<Empty> eliminarCategoria(@PathVariable("idCategoria") long id) {

		service.borrarCategoria(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idCategoria}")
	@ApiOperation(value = "Update Categoria", notes = "Categoria updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Categoria successfully updated"),
			@ApiResponse(code = 404, message = "Categoria not found") })
	public ResponseEntity<Categoria> updateCategoria(@PathVariable("idCategoria") Long id, CategoriaVo categoriaVo) {

		return new ResponseEntity<>(service.actualizarCategoria(id, categoriaVo), HttpStatus.OK);
	}

}
