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

import com.unla.reactivar.models.Funcion;
import com.unla.reactivar.services.FuncionService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.FuncionVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/funcion")
@Api(tags = "Funcion")
@CrossOrigin(origins = "*")
public class FuncionController {

	@Autowired
	private FuncionService service;

	@GetMapping
	@ApiOperation(value = "Listar todas las Funciones", notes = "Servicio para listar todas las Funciones")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Funciones encontradas"),
			@ApiResponse(code = 404, message = "Funciones no encontradas") })
	public List<Funcion> traerTodos() {
		return service.traerTodasFunciones();
	}

	@GetMapping("/{idFuncion}")
	@ApiOperation(value = "Mostrar una Función por ID", notes = "Servicio para mostrar un Función a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Función encontrada"),
			@ApiResponse(code = 404, message = "Función no encontrada") })
	public Funcion traerFuncion(@PathVariable("idFuncion") long id) {
		return service.traerFuncionPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear una Función", notes = "Servicio para crear una Función")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Función creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Función") })
	public ResponseEntity<Funcion> crearFuncion(@RequestBody FuncionVo funcionVo) {
		Funcion funcion = service.crearFuncion(funcionVo);

		return new ResponseEntity<>(funcion, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idFuncion}")
	@ApiOperation(value = "Eliminar una Función por ID", notes = "Servicio para eliminar una Función a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Función eliminada con exito"),
			@ApiResponse(code = 404, message = "Función no encontrada") })
	public ResponseEntity<Empty> eliminarFuncion(@PathVariable("idFuncion") long id) {

		service.borrarFuncion(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idFuncion}")
	@ApiOperation(value = "Modificar una Función por ID", notes = "Servicio para modificar una Función a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Función modificada con exito"),
			@ApiResponse(code = 404, message = "Función no encontrada") })
	public ResponseEntity<Funcion> updateFuncion(@PathVariable("idFuncion") Long id, @RequestBody FuncionVo funcionVo) {

		return new ResponseEntity<>(service.actualizarFuncion(id, funcionVo), HttpStatus.OK);
	}

}
