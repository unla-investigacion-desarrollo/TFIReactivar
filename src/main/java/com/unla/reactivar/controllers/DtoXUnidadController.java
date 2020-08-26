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

import com.unla.reactivar.models.DtoXUnidad;
import com.unla.reactivar.models.Promocion;
import com.unla.reactivar.services.DtoXUnidadService;
import com.unla.reactivar.vo.DtoXUnidadVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/dtoXUnidad")
@Api(tags = "DtoXUnidad")
public class DtoXUnidadController {
	
	@Autowired
	private DtoXUnidadService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los dtoXUnidads", notes = "Service para listar todos los dtoXUnidads")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXUnidads encontrados"),
			@ApiResponse(code = 404, message = "DtoXUnidads no encontrados") })
	public List<DtoXUnidad> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idDtoXUnidad}")
	@ApiOperation(value = "Mostrar un dtoXUnidad", notes = "Service para mostrar un dtoXUnidad")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXUnidad encontrado"),
			@ApiResponse(code = 404, message = "DtoXUnidad no encontrado") })
	public Promocion traerDtoXUnidad(@PathVariable ("idDtoXUnidad") long id) {
		return service.traerDtoXUnidadPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear DtoXUnidad", notes = "Servicio creador de dtoXUnidads")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXUnidad successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<DtoXUnidad> crearDtoXUnidad(@RequestBody DtoXUnidadVo dtoXUnidadVo){
		DtoXUnidad dtoXUnidad = service.crearDtoXUnidad(dtoXUnidadVo);
		
		return new ResponseEntity<>(dtoXUnidad, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idDtoXUnidad}")
	@ApiOperation(value = "Eliminar dtoXUnidad", notes = "Servicio elimina DtoXUnidad")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "DtoXUnidad eliminado con exito"),
			@ApiResponse(code = 404, message = "DtoXUnidad no encontrado") })
	public void eliminarDtoXUnidad(@PathVariable("idDtoXUnidad") long id ) {
		
		service.borrarDtoXUnidad(id);
	}
	
	@PutMapping("/{idDtoXUnidad}")
	@ApiOperation(value = "Update DtoXUnidad", notes = "DtoXUnidad updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "DtoXUnidad successfully updated"),
			@ApiResponse(code = 404, message = "DtoXUnidad not found") })
	public ResponseEntity<Promocion> updateDtoXUnidad(@PathVariable("idDtoXUnidad") Long id, DtoXUnidadVo dtoXUnidadVo) {

		return new ResponseEntity<>(service.actualizarDtoXUnidad(id, dtoXUnidadVo), HttpStatus.OK);
	}

}
