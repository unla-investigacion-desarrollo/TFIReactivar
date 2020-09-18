package com.unla.reactivar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.Promocion;
import com.unla.reactivar.services.PromocionService;
import com.unla.reactivar.vo.Empty;

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
	@ApiOperation(value = "Listar todas las Promociones", notes = "Servicio para listar todas las Promociones")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Promociones encontradas"),
			@ApiResponse(code = 404, message = "Promociones no encontradas") })
	public List<Promocion> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idPromocion}")
	@ApiOperation(value = "Mostrar una Promoción por ID", notes = "Servicio para mostrar una Promoción a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Promoción encontrada"),
			@ApiResponse(code = 404, message = "Promoción no encontrada") })
	public Promocion traerPromocion(@PathVariable("idPromocion") long id) {
		return service.traerPromocionPorId(id);
	}

	@DeleteMapping("/{idPromocion}")
	@ApiOperation(value = "Eliminar una Promoción por ID", notes = "Servicio para eliminar una Promoción a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Promoción eliminada con exito"),
			@ApiResponse(code = 404, message = "Promoción no encontrada") })
	public ResponseEntity<Empty> eliminarPromocion(@PathVariable("idPromocion") long id) {

		service.borrarPromocion(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

}
