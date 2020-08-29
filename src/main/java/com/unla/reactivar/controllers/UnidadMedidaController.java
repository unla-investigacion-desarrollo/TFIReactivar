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

import com.unla.reactivar.models.Empty;
import com.unla.reactivar.models.UnidadMedida;
import com.unla.reactivar.services.UnidadMedidaService;
import com.unla.reactivar.vo.UnidadMedidaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/unidadMedida")
@Api(tags = "UnidadMedida")
public class UnidadMedidaController {
	
	@Autowired
	private UnidadMedidaService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los unidadMedidas", notes = "Service para listar todos los unidadMedidas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "UnidadMedidas encontrados"),
			@ApiResponse(code = 404, message = "UnidadMedidas no encontrados") })
	public List<UnidadMedida> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idUnidadMedida}")
	@ApiOperation(value = "Mostrar un unidadMedida", notes = "Service para mostrar un unidadMedida")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "UnidadMedida encontrado"),
			@ApiResponse(code = 404, message = "UnidadMedida no encontrado") })
	public UnidadMedida traerUnidadMedida(@PathVariable ("idUnidadMedida") long id) {
		return service.traerUnidadMedidaPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear UnidadMedida", notes = "Servicio creador de unidadMedidas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "UnidadMedida successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<UnidadMedida> crearUnidadMedida(@RequestBody UnidadMedidaVo unidadMedidaVo){
		UnidadMedida unidadMedida = service.crearUnidadMedida(unidadMedidaVo);
		
		return new ResponseEntity<>(unidadMedida, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idUnidadMedida}")
	@ApiOperation(value = "Eliminar unidadMedida", notes = "Servicio elimina UnidadMedida")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "UnidadMedida eliminado con exito"),
			@ApiResponse(code = 404, message = "UnidadMedida no encontrado") })
	public ResponseEntity<Empty> eliminarUnidadMedida(@PathVariable("idUnidadMedida") long id ) {
		
		service.borrarUnidadMedida(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idUnidadMedida}")
	@ApiOperation(value = "Update UnidadMedida", notes = "UnidadMedida updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "UnidadMedida successfully updated"),
			@ApiResponse(code = 404, message = "UnidadMedida not found") })
	public ResponseEntity<UnidadMedida> updateUnidadMedida(@PathVariable("idUnidadMedida") Long id, UnidadMedidaVo unidadMedidaVo) {

		return new ResponseEntity<>(service.actualizarUnidadMedida(id, unidadMedidaVo), HttpStatus.OK);
	}

}
