package com.unla.alimentar.controllers;

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

import com.unla.alimentar.models.OcupacionLocal;
import com.unla.alimentar.services.OcupacionLocalService;
import com.unla.alimentar.vo.OcupacionLocalVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/ocupacionLocal")
@Api(tags = "OcupacionLocal")
public class OcupacionLocalController {
	
	@Autowired
	private OcupacionLocalService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los ocupacionLocals", notes = "Service para listar todos los ocupacionLocals")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OcupacionLocals encontrados"),
			@ApiResponse(code = 404, message = "OcupacionLocals no encontrados") })
	public List<OcupacionLocal> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idOcupacionLocal}")
	@ApiOperation(value = "Mostrar un ocupacionLocal", notes = "Service para mostrar un ocupacionLocal")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OcupacionLocal encontrado"),
			@ApiResponse(code = 404, message = "OcupacionLocal no encontrado") })
	public OcupacionLocal traerOcupacionLocal(@PathVariable ("idOcupacionLocal") long id) {
		return service.traerOcupacionLocalPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear OcupacionLocal", notes = "Servicio creador de ocupacionLocals")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OcupacionLocal successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<OcupacionLocal> crearOcupacionLocal(@RequestBody OcupacionLocalVo ocupacionLocalVo){
		OcupacionLocal ocupacionLocal = service.crearOcupacionLocal(ocupacionLocalVo);
		
		return new ResponseEntity<>(ocupacionLocal, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idOcupacionLocal}")
	@ApiOperation(value = "Eliminar ocupacionLocal", notes = "Servicio elimina OcupacionLocal")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OcupacionLocal eliminado con exito"),
			@ApiResponse(code = 404, message = "OcupacionLocal no encontrado") })
	public void eliminarOcupacionLocal(@PathVariable("idOcupacionLocal") long id ) {
		
		service.borrarOcupacionLocal(id);
	}
	
	@PutMapping("/{idOcupacionLocal}")
	@ApiOperation(value = "Update OcupacionLocal", notes = "OcupacionLocal updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OcupacionLocal successfully updated"),
			@ApiResponse(code = 404, message = "OcupacionLocal not found") })
	public ResponseEntity<OcupacionLocal> updateOcupacionLocal(@PathVariable("idOcupacionLocal") Long id, OcupacionLocalVo ocupacionLocalVo) {

		return new ResponseEntity<>(service.actualizarOcupacionLocal(id, ocupacionLocalVo), HttpStatus.OK);
	}

}