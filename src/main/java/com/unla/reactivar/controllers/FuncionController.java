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

import com.unla.reactivar.models.Funcion;
import com.unla.reactivar.services.FuncionService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.FuncionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/funcion")
@Api(tags = "Funcion")
public class FuncionController {
	
	@Autowired
	private FuncionService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los funcions", notes = "Service para listar todos los funcions")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Funcions encontrados"),
			@ApiResponse(code = 404, message = "Funcions no encontrados") })
	public List<Funcion> traerTodasFunciones() {
		return service.traerTodasFunciones();
	}
	
	@GetMapping("/{idFuncion}")
	@ApiOperation(value = "Mostrar un funcion", notes = "Service para mostrar un funcion")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Funcion encontrado"),
			@ApiResponse(code = 404, message = "Funcion no encontrado") })
	public Funcion traerFuncion(@PathVariable ("idFuncion") long id) {
		return service.traerFuncionPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Funcion", notes = "Servicio creador de funcions")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Funcion successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Funcion> crearFuncion(@RequestBody FuncionVo funcionVo){
		Funcion funcion = service.crearFuncion(funcionVo);
		
		return new ResponseEntity<>(funcion, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idFuncion}")
	@ApiOperation(value = "Eliminar funcion", notes = "Servicio elimina Funcion")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Funcion eliminado con exito"),
			@ApiResponse(code = 404, message = "Funcion no encontrado") })
	public ResponseEntity<Empty> eliminarFuncion(@PathVariable("idFuncion") long id ) {
		
		service.borrarFuncion(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idFuncion}")
	@ApiOperation(value = "Update Funcion", notes = "Funcion updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Funcion successfully updated"),
			@ApiResponse(code = 404, message = "Funcion not found") })
	public ResponseEntity<Funcion> updateFuncion(@PathVariable("idFuncion") Long id, FuncionVo funcionVo) {

		return new ResponseEntity<>(service.actualizarFuncion(id, funcionVo), HttpStatus.OK);
	}

}
