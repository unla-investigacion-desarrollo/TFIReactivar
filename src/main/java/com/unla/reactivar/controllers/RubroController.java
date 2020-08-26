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

import com.unla.reactivar.models.Rubro;
import com.unla.reactivar.services.RubroService;
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

	@GetMapping
	@ApiOperation(value = "Listar todos los rubros", notes = "Service para listar todos los rubros")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Rubros encontrados"),
			@ApiResponse(code = 404, message = "Rubros no encontrados") })
	public List<Rubro> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idRubro}")
	@ApiOperation(value = "Mostrar un rubro", notes = "Service para mostrar un rubro")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Rubro encontrado"),
			@ApiResponse(code = 404, message = "Rubro no encontrado") })
	public Rubro traerRubro(@PathVariable("idRubro") long id) {
		return service.traerRubroPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear Rubro", notes = "Servicio creador de rubros")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Rubro successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Rubro> crearRubro(@RequestBody RubroVo rubroVo) {
		Rubro rubro = service.crearRubro(rubroVo);

		return new ResponseEntity<>(rubro, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idRubro}")
	@ApiOperation(value = "Eliminar rubro", notes = "Servicio elimina Rubro")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Rubro eliminado con exito"),
			@ApiResponse(code = 404, message = "Rubro no encontrado") })
	public void eliminarRubro(@PathVariable("idRubro") long id) {

		service.borrarRubro(id);
	}

	@PutMapping("/{idRubro}")
	@ApiOperation(value = "Update Rubro", notes = "Rubro updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Rubro successfully updated"),
			@ApiResponse(code = 404, message = "Rubro not found") })
	public ResponseEntity<Rubro> updateRubro(@PathVariable("idRubro") Long id, RubroVo rubroVo) {

		return new ResponseEntity<>(service.actualizarRubro(id, rubroVo), HttpStatus.OK);
	}

}
