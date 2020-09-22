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

import com.unla.reactivar.models.OcupacionLocal;
import com.unla.reactivar.services.OcupacionLocalService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.OcupacionLocalDniVo;
import com.unla.reactivar.vo.OcupacionLocalVo;

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
	@ApiOperation(value = "Listar todas las Ocupaciones Local", notes = "Servicio para listar todas las Ocupaciones Local")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ocupaciones Local encontradas"),
			@ApiResponse(code = 404, message = "Ocupaciones Local no encontradas") })
	public List<OcupacionLocal> traerTodos() {
		return service.traerTodasOcupacionesLocales();
	}

	@PostMapping
	@ApiOperation(value = "Marcar Entrada/Salida Ocupación Local QR", notes = "Servicio para marcar la Entrada/Salida de Ocupación Local")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ocupación Local de Entrada/Salida creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Ocupación Local") })
	public ResponseEntity<OcupacionLocal> crearOcupacionLocal(@RequestBody OcupacionLocalVo ocupacionLocalVo) {
		OcupacionLocal ocupacionLocal = service.crearOcupacionLocal(ocupacionLocalVo);

		return new ResponseEntity<>(ocupacionLocal, HttpStatus.CREATED);
	}
	
	@PostMapping("/dni")
	@ApiOperation(value = "Marcar Entrada/Salida Ocupación Local DNI", notes = "Servicio para marcar la Entrada/Salida de Ocupación Local")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Ocupación Local de Entrada/Salida creada exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Ocupación Local") })
	public ResponseEntity<OcupacionLocal> crearOcupacionLocalDni(@RequestBody OcupacionLocalDniVo ocupacionLocalDniVo) {
		OcupacionLocal ocupacionLocal = service.crearOcupacionLocalDni(ocupacionLocalDniVo);

		return new ResponseEntity<>(ocupacionLocal, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idOcupacionLocal}")
	@ApiOperation(value = "Eliminar una Ocupación Local por Id", notes = "Servicio para eliminar una Ocupación Local a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ocupación Local eliminada con exito"),
			@ApiResponse(code = 404, message = "Ocupación Local no encontrada") })
	public ResponseEntity<Empty> eliminarOcupacionLocal(@PathVariable("idOcupacionLocal") long id) {

		service.borrarOcupacionLocal(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idOcupacionLocal}")
	@ApiOperation(value = "Modificar una Ocupación Local por ID", notes = "Servicio para modificar una Ocupación Local a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ocupación Local modificada exitosamente"),
			@ApiResponse(code = 404, message = "Ocupación Local no encontrada") })
	public ResponseEntity<OcupacionLocal> updateOcupacionLocal(@PathVariable("idOcupacionLocal") Long id,
			OcupacionLocalVo ocupacionLocalVo) {

		return new ResponseEntity<>(service.actualizarOcupacionLocal(id, ocupacionLocalVo), HttpStatus.OK);
	}

}
