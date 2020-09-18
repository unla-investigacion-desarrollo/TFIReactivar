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

import com.unla.reactivar.models.EstadoCarrito;
import com.unla.reactivar.services.EstadoCarritoService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.EstadoCarritoVo;

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
	@ApiOperation(value = "Listar todos los Estados Carritos", notes = "Servicio para listar todos los Estados Carritos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Estados Carritos encontrados"),
			@ApiResponse(code = 404, message = "Estados Carritos no encontrados") })
	public List<EstadoCarrito> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idEstadoCarrito}")
	@ApiOperation(value = "Mostrar un Estado Carrito por ID", notes = "Servicio para mostrar un Estado Carrito a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Estado Carrito encontrado"),
			@ApiResponse(code = 404, message = "Estado Carrito no encontrado") })
	public EstadoCarrito traerEstadoCarrito(@PathVariable ("idEstadoCarrito") long id) {
		return service.traerEstadoCarritoPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear un Estado Carrito", notes = "Servicio para crear un Estado Carrito")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Estado Carrito creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Estado Carrito") })
	public ResponseEntity<EstadoCarrito> crearEstadoCarrito(@RequestBody EstadoCarritoVo estadoCarritoVo){
		EstadoCarrito estadoCarrito = service.crearEstadoCarrito(estadoCarritoVo);
		
		return new ResponseEntity<>(estadoCarrito, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idEstadoCarrito}")
	@ApiOperation(value = "Eliminar Estado Carrito por ID", notes = "Servicio para eliminar un Estado Carrito a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Estado Carrito eliminado con exito"),
			@ApiResponse(code = 404, message = "Estado Carrito no encontrado") })
	public ResponseEntity<Empty> eliminarEstadoCarrito(@PathVariable("idEstadoCarrito") long id ) {
		
		service.borrarEstadoCarrito(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idEstadoCarrito}")
	@ApiOperation(value = "Modificar Estado Carrito por ID", notes = "Servicio para modificar un Estado Carrito a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Estado Carrito modificado con exito"),
			@ApiResponse(code = 404, message = "Estado Carrito no encontrado") })
	public ResponseEntity<EstadoCarrito> updateEstadoCarrito(@PathVariable("idEstadoCarrito") Long id, EstadoCarritoVo estadoCarritoVo) {

		return new ResponseEntity<>(service.actualizarEstadoCarrito(id, estadoCarritoVo), HttpStatus.OK);
	}

}
