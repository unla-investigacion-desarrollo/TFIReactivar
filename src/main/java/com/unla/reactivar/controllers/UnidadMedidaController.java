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

import com.unla.reactivar.models.UnidadMedida;
import com.unla.reactivar.services.UnidadMedidaService;
import com.unla.reactivar.vo.Empty;
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
	@ApiOperation(value = "Listar todas las Unidades de Medida", notes = "Servicio para listar todas las Unidades de Medida")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Unidades de Medida encontradas"),
			@ApiResponse(code = 404, message = "Unidades de Medida no encontradas") })
	public List<UnidadMedida> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idUnidadMedida}")
	@ApiOperation(value = "Mostrar una Unidad de Medida por ID", notes = "Servicio para mostrar una Unidad de Medida a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Unidad de Medida encontrada"),
			@ApiResponse(code = 404, message = "Unidad de Medida no encontrada") })
	public UnidadMedida traerUnidadMedida(@PathVariable("idUnidadMedida") long id) {
		return service.traerUnidadMedidaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una Unidad de Medida", notes = "Servicio para crear una Unidad de Medida")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Unidad de Medida creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Unidad de Medida") })
	public ResponseEntity<UnidadMedida> crearUnidadMedida(@RequestBody UnidadMedidaVo unidadMedidaVo) {
		UnidadMedida unidadMedida = service.crearUnidadMedida(unidadMedidaVo);

		return new ResponseEntity<>(unidadMedida, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idUnidadMedida}")
	@ApiOperation(value = "Eliminar una Unidad de Medida por ID", notes = "Servicio para eliminar una Unidad de Medida a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Unidad de Medida eliminada con exito"),
			@ApiResponse(code = 404, message = "Unidad de Medida no encontrada") })
	public ResponseEntity<Empty> eliminarUnidadMedida(@PathVariable("idUnidadMedida") long id) {

		service.borrarUnidadMedida(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idUnidadMedida}")
	@ApiOperation(value = "Modificar una Unidad de Medida por ID", notes = "Servicio para modificar una Unidad de Medida a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Unidad de Medida modificada exitosamente"),
			@ApiResponse(code = 404, message = "Unidad de Medida no encontrada") })
	public ResponseEntity<UnidadMedida> updateUnidadMedida(@PathVariable("idUnidadMedida") Long id,
			UnidadMedidaVo unidadMedidaVo) {

		return new ResponseEntity<>(service.actualizarUnidadMedida(id, unidadMedidaVo), HttpStatus.OK);
	}

}
