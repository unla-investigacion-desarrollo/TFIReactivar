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

import com.unla.alimentar.models.Provincia;
import com.unla.alimentar.services.ProvinciaService;
import com.unla.alimentar.vo.ProvinciaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/provincia")
@Api(tags = "Provincia")
public class ProvinciaController {
	
	@Autowired
	private ProvinciaService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los provincias", notes = "Service para listar todos los provincias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Provincias encontrados"),
			@ApiResponse(code = 404, message = "Provincias no encontrados") })
	public List<Provincia> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idProvincia}")
	@ApiOperation(value = "Mostrar un provincia", notes = "Service para mostrar un provincia")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Provincia encontrado"),
			@ApiResponse(code = 404, message = "Provincia no encontrado") })
	public Provincia traerProvincia(@PathVariable ("idProvincia") long id) {
		return service.traerProvinciaPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Provincia", notes = "Servicio creador de provincias")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Provincia successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Provincia> crearProvincia(@RequestBody ProvinciaVo provinciaVo){
		Provincia provincia = service.crearProvincia(provinciaVo);
		
		return new ResponseEntity<>(provincia, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idProvincia}")
	@ApiOperation(value = "Eliminar provincia", notes = "Servicio elimina Provincia")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Provincia eliminado con exito"),
			@ApiResponse(code = 404, message = "Provincia no encontrado") })
	public void eliminarProvincia(@PathVariable("idProvincia") long id ) {
		
		service.borrarProvincia(id);
	}
	
	@PutMapping("/{idProvincia}")
	@ApiOperation(value = "Update Provincia", notes = "Provincia updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provincia successfully updated"),
			@ApiResponse(code = 404, message = "Provincia not found") })
	public ResponseEntity<Provincia> updateProvincia(@PathVariable("idProvincia") Long id, ProvinciaVo provinciaVo) {

		return new ResponseEntity<>(service.actualizarProvincia(id, provinciaVo), HttpStatus.OK);
	}

}
