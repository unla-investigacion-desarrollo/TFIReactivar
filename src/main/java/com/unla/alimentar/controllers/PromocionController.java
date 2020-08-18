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

import com.unla.alimentar.models.Promocion;
import com.unla.alimentar.services.PromocionService;
import com.unla.alimentar.vo.PromocionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/promocion")
@Api(tags = "Promocion")
public class PromocionController {
	
	@Autowired
	private PromocionService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los promocions", notes = "Service para listar todos los promocions")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Promocions encontrados"),
			@ApiResponse(code = 404, message = "Promocions no encontrados") })
	public List<Promocion> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idPromocion}")
	@ApiOperation(value = "Mostrar un promocion", notes = "Service para mostrar un promocion")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Promocion encontrado"),
			@ApiResponse(code = 404, message = "Promocion no encontrado") })
	public Promocion traerPromocion(@PathVariable ("idPromocion") long id) {
		return service.traerPromocionPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Promocion", notes = "Servicio creador de promocions")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Promocion successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Promocion> crearPromocion(@RequestBody PromocionVo promocionVo){
		Promocion promocion = service.crearPromocion(promocionVo);
		
		return new ResponseEntity<>(promocion, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idPromocion}")
	@ApiOperation(value = "Eliminar promocion", notes = "Servicio elimina Promocion")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Promocion eliminado con exito"),
			@ApiResponse(code = 404, message = "Promocion no encontrado") })
	public void eliminarPromocion(@PathVariable("idPromocion") long id ) {
		
		service.borrarPromocion(id);
	}
	
	@PutMapping("/{idPromocion}")
	@ApiOperation(value = "Update Promocion", notes = "Promocion updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Promocion successfully updated"),
			@ApiResponse(code = 404, message = "Promocion not found") })
	public ResponseEntity<Promocion> updateAbility(@PathVariable("idPromocion") Long id, PromocionVo promocionVo) {

		return new ResponseEntity<>(service.actualizarPromocion(id, promocionVo), HttpStatus.OK);
	}

}
