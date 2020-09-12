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
	@ApiOperation(value = "Listar todos los dtoXCategorias", notes = "Service para listar todos los dtoXCategorias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXCategorias encontrados"),
			@ApiResponse(code = 404, message = "DtoXCategorias no encontrados") })
	public List<DtoXCategoria> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idDtoXCategoria}")
	@ApiOperation(value = "Mostrar un dtoXCategoria", notes = "Service para mostrar un dtoXCategoria")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXCategoria encontrado"),
			@ApiResponse(code = 404, message = "DtoXCategoria no encontrado") })
	public DtoXCategoria traerDtoXCategoria(@PathVariable ("idDtoXCategoria") long id) {
		return service.traerDtoXCategoriaPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear DtoXCategoria", notes = "Servicio creador de dtoXCategorias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXCategoria successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<DtoXCategoria> crearDtoXCategoria(@RequestBody DtoXCategoriaVo dtoXCategoriaVo){
		DtoXCategoria dtoXCategoria = service.crearDtoXCategoria(dtoXCategoriaVo);
		
		return new ResponseEntity<>(dtoXCategoria, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idDtoXCategoria}")
	@ApiOperation(value = "Eliminar dtoXCategoria", notes = "Servicio elimina DtoXCategoria")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXCategoria eliminado con exito"),
			@ApiResponse(code = 404, message = "DtoXCategoria no encontrado") })
	public ResponseEntity<Empty> eliminarDtoXCategoria(@PathVariable("idDtoXCategoria") long id ) {
		
		service.borrarDtoXCategoria(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idDtoXCategoria}")
	@ApiOperation(value = "Update DtoXCategoria", notes = "DtoXCategoria updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "DtoXCategoria successfully updated"),
			@ApiResponse(code = 404, message = "DtoXCategoria not found") })
	public ResponseEntity<DtoXCategoria> updateDtoXCategoria(@PathVariable("idDtoXCategoria") Long id, DtoXCategoriaVo dtoXCategoriaVo) {

		return new ResponseEntity<>(service.actualizarDtoXCategoria(id, dtoXCategoriaVo), HttpStatus.OK);
	}

}
