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

import com.unla.reactivar.models.Ubicacion;
import com.unla.reactivar.services.UbicacionService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.UbicacionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/ubicacion")
@Api(tags = "Ubicacion")
public class UbicacionController {

	@Autowired
	private UbicacionService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Ubicaciones", notes = "Servicio para listar todas las Ubicaciones")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ubicaciones encontradas"),
			@ApiResponse(code = 404, message = "Ubicaciones no encontradas") })
	public List<Ubicacion> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idUbicacion}")
	@ApiOperation(value = "Mostrar una Ubicación por ID", notes = "Servicio para mostrar una Ubicación")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ubicación encontrada"),
			@ApiResponse(code = 404, message = "Ubicación no encontrada") })
	public Ubicacion traerUbicacion(@PathVariable("idUbicacion") long id) {
		return service.traerUbicacionPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una Ubicación", notes = "Servicio para crear una Ubicación")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ubicación creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Ubicación") })
	public ResponseEntity<Ubicacion> crearUbicacion(@RequestBody UbicacionVo ubicacionVo) {
		Ubicacion ubicacion = service.crearUbicacion(ubicacionVo);

		return new ResponseEntity<>(ubicacion, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idUbicacion}")
	@ApiOperation(value = "Eliminar una Ubicación por ID", notes = "Servicio para eliminar una Ubicación a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ubicación eliminada con exito"),
			@ApiResponse(code = 404, message = "Ubicación no encontrada") })
	public ResponseEntity<Empty> eliminarUbicacion(@PathVariable("idUbicacion") long id) {

		service.borrarUbicacion(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idUbicacion}")
	@ApiOperation(value = "Modificar una Ubicación por ID", notes = "Servicio para modificar una Ubicación")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ubicación modificada exitosamente"),
			@ApiResponse(code = 404, message = "Ubicación no encontrada") })
	public ResponseEntity<Ubicacion> updateUbicacion(@PathVariable("idUbicacion") Long id, UbicacionVo ubicacionVo) {

		return new ResponseEntity<>(service.actualizarUbicacion(id, ubicacionVo), HttpStatus.OK);
	}

}
