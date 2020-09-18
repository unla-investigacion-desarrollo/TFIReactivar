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
import com.unla.reactivar.vo.Empty;
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
	@ApiOperation(value = "Listar todas las promociones de Lleva y Paga", notes = "Servicio para listar todas las promociones de Lleva y Paga")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Promociones Lleva y Paga encontradas"),
			@ApiResponse(code = 404, message = "Promociones Lleva y Paga no encontradas") })
	public List<LlevaPaga> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idLlevaPaga}")
	@ApiOperation(value = "Mostrar una promoción Lleva y Paga por ID", notes = "Servicio para mostrar una promoción Lleva y Paga a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Promoción Lleva y Paga encontrada"),
			@ApiResponse(code = 404, message = "Promoción Lleva y Paga no encontrada") })
	public LlevaPaga traerLlevaPaga(@PathVariable("idLlevaPaga") long id) {
		return service.traerLlevaPagaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una promoción Lleva y Paga", notes = "Servicio para crear un promoción Lleva y Paga")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Promoción Lleva y Paga creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear promoción Lleva y Paga") })
	public ResponseEntity<LlevaPaga> crearLlevaPaga(@RequestBody LlevaPagaVo llevaPagaVo) {
		LlevaPaga llevaPaga = service.crearLlevaPaga(llevaPagaVo);

		return new ResponseEntity<>(llevaPaga, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idLlevaPaga}")
	@ApiOperation(value = "Eliminar un promoción Lleva y Paga por ID", notes = "Servicio para eliminar una promoción Lleva y Paga a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Promoción Lleva y Paga eliminada con exito"),
			@ApiResponse(code = 404, message = "Promoción Lleva y Paga no encontrada") })
	public ResponseEntity<Empty> eliminarLlevaPaga(@PathVariable("idLlevaPaga") long id) {

		service.borrarLlevaPaga(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idLlevaPaga}")
	@ApiOperation(value = "Modificar una promoción Lleva y Paga por ID", notes = "Servicio para modificar una promoción Lleva y Paga a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Promoción Lleva y Paga modificada correctamente"),
			@ApiResponse(code = 404, message = "No se encontro promoción Lleva y Paga") })
	public ResponseEntity<LlevaPaga> updateLlevaPaga(@PathVariable("idLlevaPaga") Long id, LlevaPagaVo llevaPagaVo) {

		return new ResponseEntity<>(service.actualizarLlevaPaga(id, llevaPagaVo), HttpStatus.OK);
	}

}
