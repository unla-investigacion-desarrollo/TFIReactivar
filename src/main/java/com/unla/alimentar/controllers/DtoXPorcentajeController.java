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

import com.unla.alimentar.models.DtoXPorcentaje;
import com.unla.alimentar.services.DtoXPorcentajeService;
import com.unla.alimentar.vo.DtoXPorcentajeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/dtoXPorcentaje")
@Api(tags = "DtoXPorcentaje")
public class DtoXPorcentajeController {
	
	@Autowired
	private DtoXPorcentajeService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los dtoXPorcentajes", notes = "Service para listar todos los dtoXPorcentajes")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXPorcentajes encontrados"),
			@ApiResponse(code = 404, message = "DtoXPorcentajes no encontrados") })
	public List<DtoXPorcentaje> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idDtoXPorcentaje}")
	@ApiOperation(value = "Mostrar un dtoXPorcentaje", notes = "Service para mostrar un dtoXPorcentaje")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXPorcentaje encontrado"),
			@ApiResponse(code = 404, message = "DtoXPorcentaje no encontrado") })
	public DtoXPorcentaje traerDtoXPorcentaje(@PathVariable ("idDtoXPorcentaje") long id) {
		return service.traerDtoXPorcentajePorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear DtoXPorcentaje", notes = "Servicio creador de dtoXPorcentajes")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXPorcentaje successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<DtoXPorcentaje> crearDtoXPorcentaje(@RequestBody DtoXPorcentajeVo dtoXPorcentajeVo){
		DtoXPorcentaje dtoXPorcentaje = service.crearDtoXPorcentaje(dtoXPorcentajeVo);
		
		return new ResponseEntity<>(dtoXPorcentaje, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idDtoXPorcentaje}")
	@ApiOperation(value = "Eliminar dtoXPorcentaje", notes = "Servicio elimina DtoXPorcentaje")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXPorcentaje eliminado con exito"),
			@ApiResponse(code = 404, message = "DtoXPorcentaje no encontrado") })
	public void eliminarDtoXPorcentaje(@PathVariable("idDtoXPorcentaje") long id ) {
		
		service.borrarDtoXPorcentaje(id);
	}
	
	@PutMapping("/{idDtoXPorcentaje}")
	@ApiOperation(value = "Update DtoXPorcentaje", notes = "DtoXPorcentaje updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "DtoXPorcentaje successfully updated"),
			@ApiResponse(code = 404, message = "DtoXPorcentaje not found") })
	public ResponseEntity<DtoXPorcentaje> updateAbility(@PathVariable("idDtoXPorcentaje") Long id, DtoXPorcentajeVo dtoXPorcentajeVo) {

		return new ResponseEntity<>(service.actualizarDtoXPorcentaje(id, dtoXPorcentajeVo), HttpStatus.OK);
	}

}
