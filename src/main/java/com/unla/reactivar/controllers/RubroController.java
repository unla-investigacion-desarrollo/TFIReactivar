package com.unla.reactivar.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.unla.reactivar.models.Rubro;
import com.unla.reactivar.services.RubroService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.RubroVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/rubro")
@Api(tags = "Rubro")
public class RubroController {

	@Autowired
	private RubroService service;

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	@GetMapping
	@ApiOperation(value = "Listar todos los Rubros", notes = "Servicio para listar todos los Rubros")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Rubros encontrados"),
			@ApiResponse(code = 404, message = "Rubros no encontrados") })
	public List<Rubro> traerTodos() {
		logger.info("[RubroController - traerTodos] Se intentará traer todos los rubros.");
		return service.traerTodos();
	}

	@GetMapping("/{idRubro}")
	@ApiOperation(value = "Mostrar un Rubro por ID", notes = "Servicio para mostrar un Rubro a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Rubro encontrado"),
			@ApiResponse(code = 404, message = "Rubro no encontrado") })
	public Rubro traerRubro(@PathVariable("idRubro") long id) {
		return service.traerRubroPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Rubro", notes = "Servicio para crear un Rubro")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Rubro creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Rubro") })
	public ResponseEntity<Rubro> crearRubro(@RequestBody RubroVo rubroVo) {
		Rubro rubro = service.crearRubro(rubroVo);

		return new ResponseEntity<>(rubro, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idRubro}")
	@ApiOperation(value = "Eliminar un Rubro por ID", notes = "Servicio para eliminar un Rubro a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Rubro eliminado con exito"),
			@ApiResponse(code = 404, message = "Rubro no encontrado") })
	public ResponseEntity<Empty> eliminarRubro(@PathVariable("idRubro") long id) {

		service.borrarRubro(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idRubro}")
	@ApiOperation(value = "Modificar un Rubro por ID", notes = "Servicio para moficar un Rubro a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Rubro modificado exitosamente"),
			@ApiResponse(code = 404, message = "Rubro no encontrado") })
	public ResponseEntity<Rubro> updateRubro(@PathVariable("idRubro") Long id, RubroVo rubroVo) {

		return new ResponseEntity<>(service.actualizarRubro(id, rubroVo), HttpStatus.OK);
	}

}
