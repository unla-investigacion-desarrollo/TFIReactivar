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

import com.unla.reactivar.models.TipoEmprendimiento;
import com.unla.reactivar.services.TipoEmprendimientoService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.TipoEmprendimientoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/tipoEmprendimiento")
@Api(tags = "TipoEmprendimiento")
@CrossOrigin(origins = "*")
public class TipoEmprendimientoController {

	@Autowired
	private TipoEmprendimientoService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Tipos de Emprendimiento", notes = "Servicio para listar todos los Tipos de Emprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tipos de Emprendimiento encontrados"),
			@ApiResponse(code = 404, message = "Tipos de Emprendimiento no encontrados") })
	public List<TipoEmprendimiento> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idTipoEmprendimiento}")
	@ApiOperation(value = "Mostrar un Tipo de Emprendimiento por ID", notes = "Servicio para mostrar un Tipo de Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tipo de Emprendimiento encontrado"),
			@ApiResponse(code = 404, message = "Tipo de Emprendimiento no encontrado") })
	public TipoEmprendimiento traerTipoEmprendimiento(@PathVariable("idTipoEmprendimiento") long id) {
		return service.traerTipoEmprendimientoPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear un Tipo de Emprendimiento", notes = "Servicio para crear un Tipo de Emprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Tipo de Emprendimiento creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Tipo de Emprendimiento") })
	public ResponseEntity<TipoEmprendimiento> crearTipoEmprendimiento(
			@RequestBody TipoEmprendimientoVo tipoEmprendimientoVo) {
		TipoEmprendimiento tipoEmprendimiento = service.crearTipoEmprendimiento(tipoEmprendimientoVo);

		return new ResponseEntity<>(tipoEmprendimiento, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idTipoEmprendimiento}")
	@ApiOperation(value = "Eliminar un Tipo de Emprendimiento por ID", notes = "Servicio para eliminar un Tipo de Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tipo de Emprendimiento eliminado con exito"),
			@ApiResponse(code = 404, message = "Tipo Emprendimiento no encontrado") })
	public ResponseEntity<Empty> eliminarTipoEmprendimiento(@PathVariable("idTipoEmprendimiento") long id) {

		service.borrarTipoEmprendimiento(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idTipoEmprendimiento}")
	@ApiOperation(value = "Modificar un Tipo de Emprendimiento por ID", notes = "Servicio para modificar un Tipo de Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tipo de Emprendimiento creado exitosamente"),
			@ApiResponse(code = 404, message = "Tipo de Emprendimiento no encontrado") })
	public ResponseEntity<TipoEmprendimiento> updateTipoEmprendimiento(@PathVariable("idTipoEmprendimiento") Long id,
			@RequestBody TipoEmprendimientoVo tipoEmprendimientoVo) {

		return new ResponseEntity<>(service.actualizarTipoEmprendimiento(id, tipoEmprendimientoVo), HttpStatus.OK);
	}

}
