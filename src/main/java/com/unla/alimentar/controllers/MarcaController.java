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

import com.unla.alimentar.models.Marca;
import com.unla.alimentar.services.MarcaService;
import com.unla.alimentar.vo.MarcaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/marca")
@Api(tags = "Marca")
public class MarcaController {
	
	@Autowired
	private MarcaService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los marcas", notes = "Service para listar todos los marcas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Marcas encontrados"),
			@ApiResponse(code = 404, message = "Marcas no encontrados") })
	public List<Marca> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idMarca}")
	@ApiOperation(value = "Mostrar un marca", notes = "Service para mostrar un marca")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Marca encontrado"),
			@ApiResponse(code = 404, message = "Marca no encontrado") })
	public Marca traerMarca(@PathVariable ("idMarca") long id) {
		return service.traerMarcaPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Marca", notes = "Servicio creador de marcas")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Marca successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Marca> crearMarca(@RequestBody MarcaVo marcaVo){
		Marca marca = service.crearMarca(marcaVo);
		
		return new ResponseEntity<>(marca, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idMarca}")
	@ApiOperation(value = "Eliminar marca", notes = "Servicio elimina Marca")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Marca eliminado con exito"),
			@ApiResponse(code = 404, message = "Marca no encontrado") })
	public void eliminarMarca(@PathVariable("idMarca") long id ) {
		
		service.borrarMarca(id);
	}
	
	@PutMapping("/{idMarca}")
	@ApiOperation(value = "Update Marca", notes = "Marca updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Marca successfully updated"),
			@ApiResponse(code = 404, message = "Marca not found") })
	public ResponseEntity<Marca> updateMarca(@PathVariable("idMarca") Long id, MarcaVo marcaVo) {

		return new ResponseEntity<>(service.actualizarMarca(id, marcaVo), HttpStatus.OK);
	}

}
