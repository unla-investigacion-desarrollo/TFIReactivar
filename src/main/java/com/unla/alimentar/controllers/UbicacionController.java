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

import com.unla.alimentar.models.Ubicacion;
import com.unla.alimentar.services.UbicacionService;
import com.unla.alimentar.vo.UbicacionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/ubicacion")
@Api(tags = "Ubicacion")
public class UbicacionController {
	
	@Autowired
	private UbicacionService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los ubicacions", notes = "Service para listar todos los ubicacions")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ubicacions encontrados"),
			@ApiResponse(code = 404, message = "Ubicacions no encontrados") })
	public List<Ubicacion> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idUbicacion}")
	@ApiOperation(value = "Mostrar un ubicacion", notes = "Service para mostrar un ubicacion")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ubicacion encontrado"),
			@ApiResponse(code = 404, message = "Ubicacion no encontrado") })
	public Ubicacion traerUbicacion(@PathVariable ("idUbicacion") long id) {
		return service.traerUbicacionPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Ubicacion", notes = "Servicio creador de ubicacions")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ubicacion successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Ubicacion> crearUbicacion(@RequestBody UbicacionVo ubicacionVo){
		Ubicacion ubicacion = service.crearUbicacion(ubicacionVo);
		
		return new ResponseEntity<>(ubicacion, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idUbicacion}")
	@ApiOperation(value = "Eliminar ubicacion", notes = "Servicio elimina Ubicacion")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ubicacion eliminado con exito"),
			@ApiResponse(code = 404, message = "Ubicacion no encontrado") })
	public void eliminarUbicacion(@PathVariable("idUbicacion") long id ) {
		
		service.borrarUbicacion(id);
	}
	
	@PutMapping("/{idUbicacion}")
	@ApiOperation(value = "Update Ubicacion", notes = "Ubicacion updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ubicacion successfully updated"),
			@ApiResponse(code = 404, message = "Ubicacion not found") })
	public ResponseEntity<Ubicacion> updateUbicacion(@PathVariable("idUbicacion") Long id, UbicacionVo ubicacionVo) {

		return new ResponseEntity<>(service.actualizarUbicacion(id, ubicacionVo), HttpStatus.OK);
	}

}
