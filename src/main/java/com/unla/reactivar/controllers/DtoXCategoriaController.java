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

import com.unla.reactivar.models.DtoXCategoria;
import com.unla.reactivar.services.DtoXCategoriaService;
import com.unla.reactivar.vo.DtoXCategoriaVo;
import com.unla.reactivar.vo.Empty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/dtoXCategoria")
@Api(tags = "DtoXCategoria")
public class DtoXCategoriaController {

	@Autowired
	private DtoXCategoriaService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Descuentos por Categoria", notes = "servicio para listar todos los Descuentos por Categoria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuentos por Categoria encontrados"),
			@ApiResponse(code = 404, message = "Descuentos por Categoria no encontrados") })
	public List<DtoXCategoria> traerTodos() {
		return service.traerTodosDtosXCategorias();
	}

	@GetMapping("/{idDtoXCategoria}")
	@ApiOperation(value = "Mostrar un Descuento por Categoria por ID", notes = "Servicio para mostrar un Descuento por Categoria a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Categoria encontrado"),
			@ApiResponse(code = 404, message = "Descuento por Categoria no encontrado") })
	public DtoXCategoria traerDtoXCategoria(@PathVariable("idDtoXCategoria") long id) {
		return service.traerDtoXCategoriaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Descuento por Categoria", notes = "Servicio para crear un Descuento por Categoria")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Descuento por Categoria creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Descuento por Categoria") })
	public ResponseEntity<DtoXCategoria> crearDtoXCategoria(@RequestBody DtoXCategoriaVo dtoXCategoriaVo) {
		DtoXCategoria dtoXCategoria = service.crearDtoXCategoria(dtoXCategoriaVo);

		return new ResponseEntity<>(dtoXCategoria, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idDtoXCategoria}")
	@ApiOperation(value = "Eliminar Descuento por Categoria por ID", notes = "Servicio para eliminar un Descuento por Categoria a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Categoria eliminado con exito"),
			@ApiResponse(code = 404, message = "Descuento por Categoria no encontrado") })
	public ResponseEntity<Empty> eliminarDtoXCategoria(@PathVariable("idDtoXCategoria") long id) {

		service.borrarDtoXCategoria(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idDtoXCategoria}")
	@ApiOperation(value = "Modificar Descuento por Categoria por ID", notes = "Servicio para modificar Descuento por Categoria a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Categoria modificado correctamente"),
			@ApiResponse(code = 404, message = "Descuento por Categoria no encontrado") })
	public ResponseEntity<DtoXCategoria> updateDtoXCategoria(@PathVariable("idDtoXCategoria") Long id,
			@RequestBody DtoXCategoriaVo dtoXCategoriaVo) {

		return new ResponseEntity<>(service.actualizarDtoXCategoria(id, dtoXCategoriaVo), HttpStatus.OK);
	}

}
