package com.unla.reactivar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.Localidad;
import com.unla.reactivar.services.LocalidadService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.LocalidadVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/localidad")
@Api(tags = "Localidad")
@CrossOrigin(origins = "*")
public class LocalidadController {

	@Autowired
	private LocalidadService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Localidades", notes = "Servicio para listar todas las Localidades")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Localidades no encontradas"),
			@ApiResponse(code = 404, message = "Localidades no encontradas") })
	public List<Localidad> traerTodos() {
		return service.traerTodasLocalidades();
	}

	@GetMapping("/{idLocalidad}")
	@ApiOperation(value = "Mostrar una Localidad por ID", notes = "Servicio para mostrar una Localidad a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Localidad encontrada"),
			@ApiResponse(code = 404, message = "Localidad no encontrada") })
	public Localidad traerLocalidad(@PathVariable("idLocalidad") long id) {
		return service.traerLocalidadPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una Localidad", notes = "Servicio para crear una Localidad")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Localidad creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear una Localidad") })
	public ResponseEntity<Localidad> crearLocalidad(@RequestBody LocalidadVo localidadVo) {
		Localidad localidad = service.crearLocalidad(localidadVo);

		return new ResponseEntity<>(localidad, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idLocalidad}")
	@ApiOperation(value = "Eliminar una Localidad a partir de un ID", notes = "Servicio para eliminar una Localidad a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Localidad eliminada con exito"),
			@ApiResponse(code = 404, message = "Localidad no encontrada") })
	public ResponseEntity<Empty> eliminarLocalidad(@PathVariable("idLocalidad") long id) {

		service.borrarLocalidad(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idLocalidad}")
	@ApiOperation(value = "Modificar una Localidad por ID", notes = "Servicio para modificar una Localidad a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Localidad modificada correctamente"),
			@ApiResponse(code = 404, message = "Localidad no encontrada") })
	public ResponseEntity<Localidad> updateLocalidad(@PathVariable("idLocalidad") Long id, @RequestBody LocalidadVo localidadVo) {

		return new ResponseEntity<>(service.actualizarLocalidad(id, localidadVo), HttpStatus.OK);
	}

}
