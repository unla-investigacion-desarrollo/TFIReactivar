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

import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.services.ConfiguracionLocalService;
import com.unla.reactivar.vo.ConfiguracionLocalVo;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.ReqPostConfiguracionLocalVo;

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
	@ApiOperation(value = "Listar todos las Configuraciones de Locales", notes = "Service para listar todas las Configuraciones de Locales")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Configuraciones de Locales encontradas"),
			@ApiResponse(code = 404, message = "Configuraciones de Locales no encontradas") })
	public List<ConfiguracionLocal> traerTodos() {
		return service.traerTodasConfiguracionesLocales();
	}

	@GetMapping("/{idConfiguracionLocal}")
	@ApiOperation(value = "Mostrar una Configuración de Local por ID", notes = "Servicio para mostrar una Configuración Local a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Configuracion Local encontrada"),
			@ApiResponse(code = 404, message = "Configuracion Local no encontrada") })
	public ConfiguracionLocal traerConfiguracionLocal(@PathVariable("idConfiguracionLocal") long id) {
		return service.traerConfiguracionLocalPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear Configuracion Local", notes = "Servicio para crear una Configuracion Local")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Configuracion Local creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear una Configuracion Local") })
	public ResponseEntity<ConfiguracionLocal> crearConfiguracionLocal(
			@RequestBody ReqPostConfiguracionLocalVo configuracionLocalVo) {
		ConfiguracionLocal configuracionLocal = service.crearConfiguracion(configuracionLocalVo);

		return new ResponseEntity<>(configuracionLocal, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idConfiguracionLocal}")
	@ApiOperation(value = "Eliminar Configuracion Local por ID", notes = "Servicio para eliminar una Configuracion Local a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Configuracion Local eliminado con exito"),
			@ApiResponse(code = 404, message = "Configuracion Local no encontrada") })
	public ResponseEntity<Empty> eliminarConfiguracionLocal(@PathVariable("idConfiguracionLocal") long id) {

		service.borrarConfiguracionLocal(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@DeleteMapping("/{idEmprendimiento}/borrarConfiguracionesEmprendimiento")
	@ApiOperation(value = "Eliminar lista de Configuracion Locale por ID Emprendimiento", notes = "Servicio para eliminar una lista de Configuracion Local a partir de un ID de Emprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Configuracion Local eliminado con exito"),
			@ApiResponse(code = 404, message = "Configuracion Locales no encontradas") })
	public ResponseEntity<Empty> eliminarListaConfiguracionLocal(@PathVariable("idEmprendimiento") long id) {

		service.borrarListaConfiguracionLocal(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idConfiguracionLocal}")
	@ApiOperation(value = "Modificar Configuracion Local por ID", notes = "Servicio para modificar una Configuracion Local a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Configuracion Local modificada con exito"),
			@ApiResponse(code = 404, message = "Configuracion Local no encontrada") })
	public ResponseEntity<ConfiguracionLocal> updateConfiguracionLocal(@PathVariable("idConfiguracionLocal") Long id,
			@RequestBody ConfiguracionLocalVo configuracionLocalVo) {

		return new ResponseEntity<>(service.actualizarConfiguracionLocal(id, configuracionLocalVo), HttpStatus.OK);
	}

}
