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

import com.unla.alimentar.models.EstadoCarrito;
import com.unla.alimentar.services.EstadoCarritoService;
import com.unla.alimentar.vo.EstadoCarritoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/estadoCarrito")
@Api(tags = "EstadoCarrito")
public class EstadoCarritoController {
	
	@Autowired
	private EstadoCarritoService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los estadoCarritos", notes = "Service para listar todos los estadoCarritos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoCarritos encontrados"),
			@ApiResponse(code = 404, message = "EstadoCarritos no encontrados") })
	public List<EstadoCarrito> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idEstadoCarrito}")
	@ApiOperation(value = "Mostrar un estadoCarrito", notes = "Service para mostrar un estadoCarrito")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoCarrito encontrado"),
			@ApiResponse(code = 404, message = "EstadoCarrito no encontrado") })
	public EstadoCarrito traerEstadoCarrito(@PathVariable ("idEstadoCarrito") long id) {
		return service.traerEstadoCarritoPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear EstadoCarrito", notes = "Servicio creador de estadoCarritos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoCarrito successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<EstadoCarrito> crearEstadoCarrito(@RequestBody EstadoCarritoVo estadoCarritoVo){
		EstadoCarrito estadoCarrito = service.crearEstadoCarrito(estadoCarritoVo);
		
		return new ResponseEntity<>(estadoCarrito, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idEstadoCarrito}")
	@ApiOperation(value = "Eliminar estadoCarrito", notes = "Servicio elimina EstadoCarrito")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoCarrito eliminado con exito"),
			@ApiResponse(code = 404, message = "EstadoCarrito no encontrado") })
	public void eliminarEstadoCarrito(@PathVariable("idEstadoCarrito") long id ) {
		
		service.borrarEstadoCarrito(id);
	}
	
	@PutMapping("/{idEstadoCarrito}")
	@ApiOperation(value = "Update EstadoCarrito", notes = "EstadoCarrito updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "EstadoCarrito successfully updated"),
			@ApiResponse(code = 404, message = "EstadoCarrito not found") })
	public ResponseEntity<EstadoCarrito> updateEstadoCarrito(@PathVariable("idEstadoCarrito") Long id, EstadoCarritoVo estadoCarritoVo) {

		return new ResponseEntity<>(service.actualizarEstadoCarrito(id, estadoCarritoVo), HttpStatus.OK);
	}

}
