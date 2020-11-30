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
import com.unla.reactivar.models.Provincia;
import com.unla.reactivar.services.ProvinciaService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.ProvinciaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/provincia")
@Api(tags = "Provincia")
@CrossOrigin(origins = "*")
public class ProvinciaController {

	@Autowired
	private ProvinciaService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Provincias", notes = "Servicio para listar todas las Provincias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provincias encontradas"),
			@ApiResponse(code = 404, message = "Provincias no encontradas") })
	public List<Provincia> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idProvincia}/localidades")
	@ApiOperation(value = "Listar todas las Localidades de una Provincias por ID", notes = "Servicio para listar todas las Localidades de una Provincias a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Localidades de la Provincias encontradas"),
			@ApiResponse(code = 404, message = "Localidades de la Provincia no encontradas") })
	public List<Localidad> traerLocalidades(@PathVariable("idProvincia") long id) {
		return service.traerLocalidades(id);
	}

	@GetMapping("/{idProvincia}")
	@ApiOperation(value = "Mostrar una Provincia por ID", notes = "Servicio para mostrar una Provincias a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provincia encontrada"),
			@ApiResponse(code = 404, message = "Provincia no encontrada") })
	public Provincia traerProvincia(@PathVariable("idProvincia") long id) {
		return service.traerProvinciaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una Provincia", notes = "Servicio para crear una Provincia")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Provincia creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Provincia") })
	public ResponseEntity<Provincia> crearProvincia(@RequestBody ProvinciaVo provinciaVo) {
		Provincia provincia = service.crearProvincia(provinciaVo);

		return new ResponseEntity<>(provincia, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idProvincia}")
	@ApiOperation(value = "Eliminar una Provincia por ID", notes = "Servicio para eliminar una Provincia a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provincia eliminada con exito"),
			@ApiResponse(code = 404, message = "Provincia no encontrada") })
	public ResponseEntity<Empty> eliminarProvincia(@PathVariable("idProvincia") long id) {

		service.borrarProvincia(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idProvincia}")
	@ApiOperation(value = "Modificar una Provincia por ID", notes = "Servicio para modificar una Provincia a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Provincia modificada exitosamente"),
			@ApiResponse(code = 404, message = "Provincia no encontrada") })
	public ResponseEntity<Provincia> updateProvincia(@PathVariable("idProvincia") Long id, @RequestBody ProvinciaVo provinciaVo) {

		return new ResponseEntity<>(service.actualizarProvincia(id, provinciaVo), HttpStatus.OK);
	}

}
