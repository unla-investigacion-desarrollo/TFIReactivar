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

import com.unla.reactivar.models.EstadoEmprendimiento;
import com.unla.reactivar.services.EstadoEmprendimientoService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.EstadoEmprendimientoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/estadoEmprendimiento")
@Api(tags = "EstadoEmprendimiento")
@CrossOrigin(origins = "*")
public class EstadoEmprendimientoController {

	@Autowired
	private EstadoEmprendimientoService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los estadoEmprendimientos", notes = "Service para listar todos los estadoEmprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoEmprendimientos encontrados"),
			@ApiResponse(code = 404, message = "EstadoEmprendimientos no encontrados") })
	public List<EstadoEmprendimiento> traerTodosEstadosEmprendimiento() {
		return service.traerTodosEstadosEmprendimiento();
	}

	@GetMapping("/{idEstadoEmprendimiento}")
	@ApiOperation(value = "Mostrar un estadoEmprendimiento", notes = "Service para mostrar un estadoEmprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoEmprendimiento encontrado"),
			@ApiResponse(code = 404, message = "EstadoEmprendimiento no encontrado") })
	public EstadoEmprendimiento traerEstadoEmprendimiento(@PathVariable("idEstadoEmprendimiento") long id) {
		return service.traerEstadoEmprendimientoPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear EstadoEmprendimiento", notes = "Servicio creador de estadoEmprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoEmprendimiento successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<EstadoEmprendimiento> crearEstadoEmprendimiento(@RequestBody EstadoEmprendimientoVo estadoEmprendimientoVo) {
		EstadoEmprendimiento estadoEmprendimiento = service.crearEstadoEmprendimiento(estadoEmprendimientoVo);

		return new ResponseEntity<>(estadoEmprendimiento, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idEstadoEmprendimiento}")
	@ApiOperation(value = "Eliminar estadoEmprendimiento", notes = "Servicio elimina EstadoEmprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "EstadoEmprendimiento eliminado con exito"),
			@ApiResponse(code = 404, message = "EstadoEmprendimiento no encontrado") })
	public ResponseEntity<Empty> eliminarEstadoEmprendimiento(@PathVariable("idEstadoEmprendimiento") long id) {

		service.borrarEstadoEmprendimiento(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idEstadoEmprendimiento}")
	@ApiOperation(value = "Update EstadoEmprendimiento", notes = "EstadoEmprendimiento updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "EstadoEmprendimiento successfully updated"),
			@ApiResponse(code = 404, message = "EstadoEmprendimiento not found") })
	public ResponseEntity<EstadoEmprendimiento> updateEstadoEmprendimiento(@PathVariable("idEstadoEmprendimiento") Long id,
			@RequestBody EstadoEmprendimientoVo estadoEmprendimientoVo) {

		return new ResponseEntity<>(service.actualizarEstadoEmprendimiento(id, estadoEmprendimientoVo), HttpStatus.OK);
	}

}
