package com.unla.alimentar.controler;

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

import com.unla.alimentar.modelo.Emprendimiento;
import com.unla.alimentar.service.EmprendimientoService;
import com.unla.alimentar.vo.EmprendimientoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/emprendimiento")
@Api(tags = "Emprendimiento")
public class EmprendimientoController {
	
	@Autowired
	private EmprendimientoService service;
	
	@GetMapping
	public List<Emprendimiento> traerTodos() {
		return service.traerTodos();
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Emprendimiento", notes = "Servicio creador de emprendimientos")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Emprendimiento successfully created"),
			@ApiResponse(code = 400, message = "Invalid request") })
	public ResponseEntity<Emprendimiento> crearEmprendimiento(@RequestBody EmprendimientoVo emprendimientoVo){
		Emprendimiento emprendimiento = service.crearEmprendimiento(emprendimientoVo);
		
		return new ResponseEntity<>(emprendimiento, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{idEmprendimiento}")
	public void eliminarEmprendimiento(@PathVariable("idEmprendimiento") long id ) {
		service.borrarEmprendimiento(id);
	}
	
	@PutMapping("/{idEmprendimiento}")
	@ApiOperation(value = "Update Emprendimiento", notes = "Emprendimiento updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Emprendimiento successfully updated"),
			@ApiResponse(code = 404, message = "Emprendimiento not found") })
	public ResponseEntity<Emprendimiento> updateAbility(@PathVariable("idEmprendimiento") Long id, EmprendimientoVo emprendimientoVo) {

		return new ResponseEntity<>(service.actualizarEmprendimiento(id, emprendimientoVo), HttpStatus.OK);
	}

}
