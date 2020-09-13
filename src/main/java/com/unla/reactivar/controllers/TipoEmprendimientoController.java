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
public class TipoEmprendimientoController {
	
	@Autowired
	private TipoEmprendimientoService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los tipoEmprendimientos", notes = "Service para listar todos los tipoEmprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "TipoEmprendimientos encontrados"),
			@ApiResponse(code = 404, message = "TipoEmprendimientos no encontrados") })
	public List<TipoEmprendimiento> traerTodosTiposEmprendimientos() {
		return service.traerTodosTiposEmprendimientos();
	}
	
	@GetMapping("/{idTipoEmprendimiento}")
	@ApiOperation(value = "Mostrar un tipoEmprendimiento", notes = "Service para mostrar un tipoEmprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "TipoEmprendimiento encontrado"),
			@ApiResponse(code = 404, message = "TipoEmprendimiento no encontrado") })
	public TipoEmprendimiento traerTipoEmprendimiento(@PathVariable ("idTipoEmprendimiento") long id) {
		return service.traerTipoEmprendimientoPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear TipoEmprendimiento", notes = "Servicio creador de tipoEmprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "TipoEmprendimiento successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<TipoEmprendimiento> crearTipoEmprendimiento(@RequestBody TipoEmprendimientoVo tipoEmprendimientoVo){
		TipoEmprendimiento tipoEmprendimiento = service.crearTipoEmprendimiento(tipoEmprendimientoVo);
		
		return new ResponseEntity<>(tipoEmprendimiento, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idTipoEmprendimiento}")
	@ApiOperation(value = "Eliminar tipoEmprendimiento", notes = "Servicio elimina TipoEmprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "TipoEmprendimiento eliminado con exito"),
			@ApiResponse(code = 404, message = "TipoEmprendimiento no encontrado") })
	public ResponseEntity<Empty> eliminarTipoEmprendimiento(@PathVariable("idTipoEmprendimiento") long id ) {
		
		service.borrarTipoEmprendimiento(id);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{idTipoEmprendimiento}")
	@ApiOperation(value = "Update TipoEmprendimiento", notes = "TipoEmprendimiento updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "TipoEmprendimiento successfully updated"),
			@ApiResponse(code = 404, message = "TipoEmprendimiento not found") })
	public ResponseEntity<TipoEmprendimiento> updateTipoEmprendimiento(@PathVariable("idTipoEmprendimiento") Long id, TipoEmprendimientoVo tipoEmprendimientoVo) {

		return new ResponseEntity<>(service.actualizarTipoEmprendimiento(id, tipoEmprendimientoVo), HttpStatus.OK);
	}

}
