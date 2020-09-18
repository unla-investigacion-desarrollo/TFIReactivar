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

import com.unla.reactivar.models.DtoXUnidad;
import com.unla.reactivar.models.Promocion;
import com.unla.reactivar.services.DtoXUnidadService;
import com.unla.reactivar.vo.DtoXUnidadVo;
import com.unla.reactivar.vo.Empty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/dtoXUnidad")
@Api(tags = "DtoXUnidad")
public class DtoXUnidadController {

	@Autowired
	private DtoXUnidadService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Descuentos por Unidades", notes = "Servicio para listar todos los Descuentos por Unidades")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuentos por Unidades encontrados"),
			@ApiResponse(code = 404, message = "Descuentos por Unidades no encontrados") })
	public List<DtoXUnidad> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idDtoXUnidad}")
	@ApiOperation(value = "Mostrar un Descuento por Unidad por ID", notes = "Servicio para mostrar un Descuento por Unidad a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Unidad encontrado"),
			@ApiResponse(code = 404, message = "Descuento por Unidad no encontrado") })
	public Promocion traerDtoXUnidad(@PathVariable("idDtoXUnidad") long id) {
		return service.traerDtoXUnidadPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Descuento por Unidad", notes = "Servicio para crear un Descuento por Unidad")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Descuento por Unidad creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear un Descuento por Unidad") })
	public ResponseEntity<DtoXUnidad> crearDtoXUnidad(@RequestBody DtoXUnidadVo dtoXUnidadVo) {
		DtoXUnidad dtoXUnidad = service.crearDtoXUnidad(dtoXUnidadVo);

		return new ResponseEntity<>(dtoXUnidad, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idDtoXUnidad}")
	@ApiOperation(value = "Eliminar un Descuento por Unidad por ID", notes = "Servicio para eliminar un Descuento por Unidad a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Unidad eliminado con exito"),
			@ApiResponse(code = 404, message = "Descuento por Unidad no encontrado") })
	public ResponseEntity<Empty> eliminarDtoXUnidad(@PathVariable("idDtoXUnidad") long id) {

		service.borrarDtoXUnidad(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idDtoXUnidad}")
	@ApiOperation(value = "Modificar un Descuento por Unidad por ID", notes = "Modificar un Descuento por Unidad a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Descuento por Unidad modificado correctamente"),
			@ApiResponse(code = 404, message = "Descuento por Unidad no encontrado") })
	public ResponseEntity<Promocion> updateDtoXUnidad(@PathVariable("idDtoXUnidad") Long id,
			DtoXUnidadVo dtoXUnidadVo) {

		return new ResponseEntity<>(service.actualizarDtoXUnidad(id, dtoXUnidadVo), HttpStatus.OK);
	}

}
