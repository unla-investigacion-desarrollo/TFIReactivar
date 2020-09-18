package com.unla.reactivar.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.services.EmprendimientoService;
import com.unla.reactivar.vo.EmprendimientoVo;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.ReqPutEmprendimientoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/emprendimiento")
@Api(tags = "Emprendimiento")
public class EmprendimientoController {

	@Autowired
	private EmprendimientoService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Emprendimientos", notes = "Servicio para listar todos los Emprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimientos encontrados"),
			@ApiResponse(code = 404, message = "Emprendimientos no encontrados") })
	public List<Emprendimiento> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Mostrar un Emprendimiento por ID", notes = "Servicio para mostrar un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimiento encontrado"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public Emprendimiento traerEmprendimiento(@PathVariable("idEmprendimiento") long id) {
		return service.traerEmprendimientoPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear Emprendimiento", notes = "Servicio para crear un Emprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimiento creado exitosamente"),
			@ApiResponse(code = 400, message = "No se pudo crear Emprendimiento") })
	public ResponseEntity<Emprendimiento> crearEmprendimiento(@RequestBody EmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = service.crearEmprendimiento(emprendimientoVo);

		return new ResponseEntity<>(emprendimiento, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Eliminar emprendimiento por ID", notes = "Servicio para eliminar un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimiento eliminado con exito"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<Empty> eliminarEmprendimiento(@PathVariable("idEmprendimiento") long id) {

		service.borrarEmprendimiento(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Modificar un Emprendimiento por ID", notes = "Servicio para modificar un Emprendimiento a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento modificado correctamente"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<Emprendimiento> updateEmprendimiento(@PathVariable("idEmprendimiento") Long id,
			ReqPutEmprendimientoVo emprendimientoVo) {

		return new ResponseEntity<>(service.actualizarEmprendimiento(id, emprendimientoVo), HttpStatus.OK);
	}

	@GetMapping("/{idEmprendimiento}/exportpdf")
	@ApiOperation(value = "Exportar QR en PDF", notes = "Servicio para exportar el QR del Emprendimiento en formato PDF a partir de un ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "PDF exportado exitosamente"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado, no se pudo exportar") })
	public ResponseEntity<Empty> exportToPDF(HttpServletResponse response, @PathVariable("idEmprendimiento") Long id) {
		service.exportPDF(response, id);
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

}
