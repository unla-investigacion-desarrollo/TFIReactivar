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

import com.unla.reactivar.models.DtoXPorcentaje;
import com.unla.reactivar.services.DtoXPorcentajeService;
import com.unla.reactivar.vo.DtoXPorcentajeVo;
import com.unla.reactivar.vo.Empty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/dtoXPorcentaje")
@Api(tags = "DtoXPorcentaje")
public class DtoXPorcentajeController {

	@Autowired
	private DtoXPorcentajeService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Descuentos por Porcentajes", notes = "Servicio para listar todos los Descuento por Porcentajes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Porcentajes encontrados"),
			@ApiResponse(code = 404, message = "Descuento por Porcentajes no encontrados") })
	public List<DtoXPorcentaje> traerTodos() {
		return service.traerTodosDtosXPorcentaje();
	}

	@GetMapping("/{idDtoXPorcentaje}")
	@ApiOperation(value = "Mostrar un Descuento por Porcentaje por ID", notes = "Servicio para mostrar un Descuento por Porcentaje a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Porcentaje encontrado"),
			@ApiResponse(code = 404, message = "Descuento por Porcentaje no encontrado") })
	public DtoXPorcentaje traerDtoXPorcentaje(@PathVariable("idDtoXPorcentaje") long id) {
		return service.traerDtoXPorcentajePorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Descuento por Porcentaje", notes = "Servicio para crear un Descuento por Porcentaje")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Descuento por Porcentaje creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Descuento por Porcentaje") })
	public ResponseEntity<DtoXPorcentaje> crearDtoXPorcentaje(@RequestBody DtoXPorcentajeVo dtoXPorcentajeVo) {
		DtoXPorcentaje dtoXPorcentaje = service.crearDtoXPorcentaje(dtoXPorcentajeVo);

		return new ResponseEntity<>(dtoXPorcentaje, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idDtoXPorcentaje}")
	@ApiOperation(value = "Eliminar un Descuento por Porcentaje por ID", notes = "Servicio para eliminar un Descuento por Porcentaje a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Porcentaje eliminado con exito"),
			@ApiResponse(code = 404, message = "Descuento por Porcentaje no encontrado") })
	public ResponseEntity<Empty> eliminarDtoXPorcentaje(@PathVariable("idDtoXPorcentaje") long id) {

		service.borrarDtoXPorcentaje(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idDtoXPorcentaje}")
	@ApiOperation(value = "Modificar un Descuento por Porcentaje por ID", notes = "Servicio para modificar un Descuento por Porcentaje a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Porcentaje modificado con exito"),
			@ApiResponse(code = 404, message = "Descuento por Porcentaje no encontrado") })
	public ResponseEntity<DtoXPorcentaje> updateDtoXPorcentaje(@PathVariable("idDtoXPorcentaje") Long id,
			DtoXPorcentajeVo dtoXPorcentajeVo) {

		return new ResponseEntity<>(service.actualizarDtoXPorcentaje(id, dtoXPorcentajeVo), HttpStatus.OK);
	}

}
