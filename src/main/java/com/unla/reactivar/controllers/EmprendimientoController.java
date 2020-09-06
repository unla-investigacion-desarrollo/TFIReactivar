package com.unla.reactivar.controllers;

import java.io.IOException;
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

import com.lowagie.text.DocumentException;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.Empty;
import com.unla.reactivar.services.EmprendimientoService;
import com.unla.reactivar.vo.EmprendimientoVo;

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
	@ApiOperation(value = "Listar todos los emprendimientos", notes = "Service para listar todos los emprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimientos encontrados"),
			@ApiResponse(code = 404, message = "Emprendimientos no encontrados") })
	public List<Emprendimiento> traerTodos() {
		return service.traerTodos();
	}

	@GetMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Mostrar un emprendimiento", notes = "Service para mostrar un emprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimiento encontrado"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public Emprendimiento traerEmprendimiento(@PathVariable("idEmprendimiento") long id) {
		return service.traerEmprendimientoPorId(id);
	}

	@PostMapping
	@ApiOperation(value = "Crear Emprendimiento", notes = "Servicio creador de emprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimiento successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Emprendimiento> crearEmprendimiento(@RequestBody EmprendimientoVo emprendimientoVo) {
		Emprendimiento emprendimiento = service.crearEmprendimiento(emprendimientoVo);

		return new ResponseEntity<>(emprendimiento, HttpStatus.CREATED);
	}

	@DeleteMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Eliminar emprendimiento", notes = "Servicio elimina Emprendimiento")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimiento eliminado con exito"),
			@ApiResponse(code = 404, message = "Emprendimiento no encontrado") })
	public ResponseEntity<Empty> eliminarEmprendimiento(@PathVariable("idEmprendimiento") long id) {

		service.borrarEmprendimiento(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Update Emprendimiento", notes = "Emprendimiento updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento successfully updated"),
			@ApiResponse(code = 404, message = "Emprendimiento not found") })
	public ResponseEntity<Emprendimiento> updateEmprendimiento(@PathVariable("idEmprendimiento") Long id,
			EmprendimientoVo emprendimientoVo) {

		return new ResponseEntity<>(service.actualizarEmprendimiento(id, emprendimientoVo), HttpStatus.OK);
	}

	@GetMapping("/{idEmprendimiento}/exportpdf")
	@ApiOperation(value = "Exportar PDF", notes = "PDF exporter service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "PDF successfully exported"),
			@ApiResponse(code = 404, message = "Emprendimiento not found") })
	public void exportToPDF(HttpServletResponse response, @PathVariable("idEmprendimiento") Long id)
			throws DocumentException, IOException {
		service.exportPDF(response, id);
	}

}
