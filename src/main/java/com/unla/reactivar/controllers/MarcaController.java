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

import com.unla.reactivar.models.Marca;
import com.unla.reactivar.services.MarcaService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.MarcaVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/marca")
@Api(tags = "Marca")
@CrossOrigin(origins = "*")
public class MarcaController {

	@Autowired
	private MarcaService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Marcas", notes = "Servicio para listar todas las Marcas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Marcas encontradas"),
			@ApiResponse(code = 404, message = "Marcas no encontradas") })
	public List<Marca> traerTodos() {
		return service.traerTodasMarcas();
	}

	@GetMapping("/{idMarca}")
	@ApiOperation(value = "Mostrar una Marca por ID", notes = "Servicio para mostrar una Marca a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Marca encontrada"),
			@ApiResponse(code = 404, message = "Marca no encontrada") })
	public Marca traerMarca(@PathVariable("idMarca") long id) {
		return service.traerMarcaPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una Marca", notes = "Servicio para crear una Marca")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Marca creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Marca") })
	public ResponseEntity<Marca> crearMarca(@RequestBody MarcaVo marcaVo) {
		Marca marca = service.crearMarca(marcaVo);

		return new ResponseEntity<>(marca, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idMarca}")
	@ApiOperation(value = "Eliminar una Marca por ID", notes = "Servicio para eliminar una Marca a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Marca eliminada con exito"),
			@ApiResponse(code = 404, message = "Marca no encontrada") })
	public ResponseEntity<Empty> eliminarMarca(@PathVariable("idMarca") long id) {

		service.borrarMarca(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idMarca}")
	@ApiOperation(value = "Modificar una Marca por ID", notes = "Servicio para modificar una Marca a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Marca modificada correctamente"),
			@ApiResponse(code = 404, message = "Marca no encontrada") })
	public ResponseEntity<Marca> updateMarca(@PathVariable("idMarca") Long id, @RequestBody MarcaVo marcaVo) {

		return new ResponseEntity<>(service.actualizarMarca(id, marcaVo), HttpStatus.OK);
	}

}
