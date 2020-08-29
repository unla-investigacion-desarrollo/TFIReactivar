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

import com.unla.reactivar.models.Empty;
import com.unla.reactivar.models.Login;
import com.unla.reactivar.services.LoginService;
import com.unla.reactivar.vo.LoginVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/login")
@Api(tags = "Login")
public class LoginController {
	
	@Autowired
	private LoginService service;
	
	@GetMapping
	@ApiOperation(value = "Listar todos los logins", notes = "Service para listar todos los logins")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Logins encontrados"),
			@ApiResponse(code = 404, message = "Logins no encontrados") })
	public List<Login> traerTodos() {
		return service.traerTodos();
	}
	
	@PostMapping
	@ApiOperation(value = "Mostrar un login", notes = "Service para mostrar un login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login encontrado"),
			@ApiResponse(code = 404, message = "Login no encontrado") })
	public Login realizarLogin(@RequestBody LoginVo loginVo) {
		return service.realizarLogin(loginVo);
	}
	
	@DeleteMapping("/{email}")
	@ApiOperation(value = "Eliminar login", notes = "Servicio elimina Login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login eliminado con exito"),
			@ApiResponse(code = 404, message = "Login no encontrado") })
	public ResponseEntity<Empty> eliminarLogin(@PathVariable ("email") String email) {
		
		service.borrarLogin(email);
		
		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@PutMapping("/{email}")
	@ApiOperation(value = "Update Login", notes = "Login updater service")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login successfully updated"),
			@ApiResponse(code = 404, message = "Login not found") })
	public ResponseEntity<Login> updateLogin(@PathVariable ("email") String email, LoginVo loginVo) {

		return new ResponseEntity<>(service.actualizarLogin(email, loginVo), HttpStatus.OK);
	}

}
