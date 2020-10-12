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

import com.unla.reactivar.models.ItemCarrito;
import com.unla.reactivar.services.ItemCarritoService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.ItemCarritoVo;
import com.unla.reactivar.vo.ReqPostItemCarritoVo;

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
	@ApiOperation(value = "Listar todos los Items de Carrito", notes = "Servicio para listar todos los Items de Carrito")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Items de Carrito encontrados"),
			@ApiResponse(code = 404, message = "Items de Carrito no encontrados") })
	public List<ItemCarrito> traerTodos() {
		return service.traerTodosItemsCarrito();
	}

	@GetMapping("/{idItemCarrito}")
	@ApiOperation(value = "Mostrar un Item de Carrito por ID", notes = "Servicio para mostrar un Item de Carrito a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Item de Carrito encontrado"),
			@ApiResponse(code = 404, message = "Item de Carrito no encontrado") })
	public ItemCarrito traerItemCarrito(@PathVariable("idItemCarrito") long id) {
		return service.traerItemCarritoPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Item de Carrito", notes = "Servicio para crear un Item de Carrito")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Item de Carrito creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear un Item de Carrito") })
	public ResponseEntity<ItemCarrito> crearItemCarrito(@RequestBody ReqPostItemCarritoVo itemCarritoVo) {
		ItemCarrito itemCarrito = service.crearItemCarrito(itemCarritoVo);

		return new ResponseEntity<>(itemCarrito, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idItemCarrito}")
	@ApiOperation(value = "Eliminar un Item de Carrito por ID", notes = "Servicio para eliminar un Item de Carrito a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Item de Carrito eliminado con exito"),
			@ApiResponse(code = 404, message = "Item de Carrito no encontrado") })
	public ResponseEntity<Empty> eliminarItemCarrito(@PathVariable("idItemCarrito") long id) {

		service.borrarItemCarrito(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idItemCarrito}")
	@ApiOperation(value = "Update un Item de Carrito por ID", notes = "Servicio para modificar un Item de Carrito a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Item de Carrito modificado correctamente"),
			@ApiResponse(code = 404, message = "Item de Carrito no encontrado") })
	public ResponseEntity<ItemCarrito> updateItemCarrito(@PathVariable("idItemCarrito") Long id,
			@RequestBody ItemCarritoVo itemCarritoVo) {

		return new ResponseEntity<>(service.actualizarItemCarrito(id, itemCarritoVo), HttpStatus.OK);
	}

}
