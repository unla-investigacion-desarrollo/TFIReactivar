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

import com.unla.reactivar.models.LlevaPaga;
import com.unla.reactivar.services.LlevaPagaService;
import com.unla.reactivar.vo.LlevaPagaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/llevaPaga")
@Api(tags = "LlevaPaga")
public class LlevaPagaController {
	
	@Autowired
	private LlevaPagaService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los llevaPagas", notes = "Service para listar todos los llevaPagas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "LlevaPagas encontrados"),
			@ApiResponse(code = 404, message = "LlevaPagas no encontrados") })
	public List<LlevaPaga> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idLlevaPaga}")
	@ApiOperation(value = "Mostrar un llevaPaga", notes = "Service para mostrar un llevaPaga")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "LlevaPaga encontrado"),
			@ApiResponse(code = 404, message = "LlevaPaga no encontrado") })
	public LlevaPaga traerLlevaPaga(@PathVariable ("idLlevaPaga") long id) {
		return service.traerLlevaPagaPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear LlevaPaga", notes = "Servicio creador de llevaPagas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "LlevaPaga successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<LlevaPaga> crearLlevaPaga(@RequestBody LlevaPagaVo llevaPagaVo){
		LlevaPaga llevaPaga = service.crearLlevaPaga(llevaPagaVo);
		
		return new ResponseEntity<>(llevaPaga, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idLlevaPaga}")
	@ApiOperation(value = "Eliminar llevaPaga", notes = "Servicio elimina LlevaPaga")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "LlevaPaga eliminado con exito"),
			@ApiResponse(code = 404, message = "LlevaPaga no encontrado") })
	public void eliminarLlevaPaga(@PathVariable("idLlevaPaga") long id ) {
		
		service.borrarLlevaPaga(id);
	}
	
	@PutMapping("/{idLlevaPaga}")
	@ApiOperation(value = "Update LlevaPaga", notes = "LlevaPaga updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "LlevaPaga successfully updated"),
			@ApiResponse(code = 404, message = "LlevaPaga not found") })
	public ResponseEntity<LlevaPaga> updateLlevaPaga(@PathVariable("idLlevaPaga") Long id, LlevaPagaVo llevaPagaVo) {

		return new ResponseEntity<>(service.actualizarLlevaPaga(id, llevaPagaVo), HttpStatus.OK);
	}

}
