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

import com.unla.reactivar.models.Localidad;
import com.unla.reactivar.services.LocalidadService;
import com.unla.reactivar.vo.LocalidadVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/localidad")
@Api(tags = "Localidad")
public class LocalidadController {
	
	@Autowired
	private LocalidadService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los localidads", notes = "Service para listar todos los localidads")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Localidads encontrados"),
			@ApiResponse(code = 404, message = "Localidads no encontrados") })
	public List<Localidad> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idLocalidad}")
	@ApiOperation(value = "Mostrar un localidad", notes = "Service para mostrar un localidad")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Localidad encontrado"),
			@ApiResponse(code = 404, message = "Localidad no encontrado") })
	public Localidad traerLocalidad(@PathVariable ("idLocalidad") long id) {
		return service.traerLocalidadPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Localidad", notes = "Servicio creador de localidads")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Localidad successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Localidad> crearLocalidad(@RequestBody LocalidadVo localidadVo){
		Localidad localidad = service.crearLocalidad(localidadVo);
		
		return new ResponseEntity<>(localidad, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idLocalidad}")
	@ApiOperation(value = "Eliminar localidad", notes = "Servicio elimina Localidad")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Localidad eliminado con exito"),
			@ApiResponse(code = 404, message = "Localidad no encontrado") })
	public void eliminarLocalidad(@PathVariable("idLocalidad") long id ) {
		
		service.borrarLocalidad(id);
	}
	
	@PutMapping("/{idLocalidad}")
	@ApiOperation(value = "Update Localidad", notes = "Localidad updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Localidad successfully updated"),
			@ApiResponse(code = 404, message = "Localidad not found") })
	public ResponseEntity<Localidad> updateLocalidad(@PathVariable("idLocalidad") Long id, LocalidadVo localidadVo) {

		return new ResponseEntity<>(service.actualizarLocalidad(id, localidadVo), HttpStatus.OK);
	}

}
