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

import com.unla.alimentar.models.ItemCarrito;
import com.unla.alimentar.services.ItemCarritoService;
import com.unla.alimentar.vo.ItemCarritoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/itemCarrito")
@Api(tags = "ItemCarrito")
public class ItemCarritoController {
	
	@Autowired
	private ItemCarritoService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los itemCarritos", notes = "Service para listar todos los itemCarritos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ItemCarritos encontrados"),
			@ApiResponse(code = 404, message = "ItemCarritos no encontrados") })
	public List<ItemCarrito> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idItemCarrito}")
	@ApiOperation(value = "Mostrar un itemCarrito", notes = "Service para mostrar un itemCarrito")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ItemCarrito encontrado"),
			@ApiResponse(code = 404, message = "ItemCarrito no encontrado") })
	public ItemCarrito traerItemCarrito(@PathVariable ("idItemCarrito") long id) {
		return service.traerItemCarritoPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear ItemCarrito", notes = "Servicio creador de itemCarritos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ItemCarrito successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<ItemCarrito> crearItemCarrito(@RequestBody ItemCarritoVo itemCarritoVo){
		ItemCarrito itemCarrito = service.crearItemCarrito(itemCarritoVo);
		
		return new ResponseEntity<>(itemCarrito, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idItemCarrito}")
	@ApiOperation(value = "Eliminar itemCarrito", notes = "Servicio elimina ItemCarrito")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ItemCarrito eliminado con exito"),
			@ApiResponse(code = 404, message = "ItemCarrito no encontrado") })
	public void eliminarItemCarrito(@PathVariable("idItemCarrito") long id ) {
		
		service.borrarItemCarrito(id);
	}
	
	@PutMapping("/{idItemCarrito}")
	@ApiOperation(value = "Update ItemCarrito", notes = "ItemCarrito updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ItemCarrito successfully updated"),
			@ApiResponse(code = 404, message = "ItemCarrito not found") })
	public ResponseEntity<ItemCarrito> updateItemCarrito(@PathVariable("idItemCarrito") Long id, ItemCarritoVo itemCarritoVo) {

		return new ResponseEntity<>(service.actualizarItemCarrito(id, itemCarritoVo), HttpStatus.OK);
	}

}
