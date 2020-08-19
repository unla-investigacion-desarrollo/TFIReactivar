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

import com.unla.alimentar.models.ConfiguracionLocal;
import com.unla.alimentar.services.ConfiguracionLocalService;
import com.unla.alimentar.vo.ConfiguracionLocalVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/configuracionLocal")
@Api(tags = "ConfiguracionLocal")
public class ConfiguracionLocalController {
	
	@Autowired
	private ConfiguracionLocalService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los configuracionLocals", notes = "Service para listar todos los configuracionLocals")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ConfiguracionLocals encontrados"),
			@ApiResponse(code = 404, message = "ConfiguracionLocals no encontrados") })
	public List<ConfiguracionLocal> traerTodos() {
		return service.traerTodos();
	}
	
	@GetMapping("/{idConfiguracionLocal}")
	@ApiOperation(value = "Mostrar un configuracionLocal", notes = "Service para mostrar un configuracionLocal")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ConfiguracionLocal encontrado"),
			@ApiResponse(code = 404, message = "ConfiguracionLocal no encontrado") })
	public ConfiguracionLocal traerConfiguracionLocal(@PathVariable ("idConfiguracionLocal") long id) {
		return service.traerConfiguracionLocalPorId(id);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear ConfiguracionLocal", notes = "Servicio creador de configuracionLocales")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ConfiguracionLocal successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<ConfiguracionLocal> crearConfiguracionLocal(@RequestBody ConfiguracionLocalVo configuracionLocalVo){
		ConfiguracionLocal configuracionLocal = service.crearConfiguracion(configuracionLocalVo);
		
		return new ResponseEntity<>(configuracionLocal, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idConfiguracionLocal}")
	@ApiOperation(value = "Eliminar configuracionLocal", notes = "Servicio elimina ConfiguracionLocal")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "ConfiguracionLocal eliminado con exito"),
			@ApiResponse(code = 404, message = "ConfiguracionLocal no encontrado") })
	public void eliminarConfiguracionLocal(@PathVariable("idConfiguracionLocal") long id ) {
		
		service.borrarConfiguracionLocal(id);
	}
	
	@PutMapping("/{idConfiguracionLocal}")
	@ApiOperation(value = "Update ConfiguracionLocal", notes = "ConfiguracionLocal updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ConfiguracionLocal successfully updated"),
			@ApiResponse(code = 404, message = "ConfiguracionLocal not found") })
	public ResponseEntity<ConfiguracionLocal> updateConfiguracionLocal(@PathVariable("idConfiguracionLocal") Long id, ConfiguracionLocalVo configuracionLocalVo) {

		return new ResponseEntity<>(service.actualizarConfiguracionLocal(id, configuracionLocalVo), HttpStatus.OK);
	}

}
