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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.Login;
import com.unla.reactivar.services.LoginService;
import com.unla.reactivar.vo.Empty;
import com.unla.reactivar.vo.LoginPostResVo;
import com.unla.reactivar.vo.LoginVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/login")
@Api(tags = "Login")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private LoginService service;

	@GetMapping
	@ApiOperation(value = "Listar todos los Logins", notes = "Servicio para listar todos los Logins")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Logins encontrados"),
			@ApiResponse(code = 404, message = "Logins no encontrados") })
	public List<Login> traerTodosLogins() {
		return service.traerTodosLogins();
	}

	@PostMapping
	@ApiOperation(value = "Login", notes = "Servicio para realizar un Login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login realizado con exito"),
			@ApiResponse(code = 404, message = "Login ha fallado") })
	public LoginPostResVo realizarLogin(@RequestBody LoginVo loginVo) {
		return service.realizarLogin(loginVo);
	}

	@PostMapping("/token")
	@ApiOperation(value = "Token Login", notes = "Servicio para realizar un Login con token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login realizado con exito"),
			@ApiResponse(code = 404, message = "Login ha fallado") })
	public LoginPostResVo realizarTokenLogin(@RequestParam String token) {
		return service.realizarLoginToken(token);
	}
	
	@DeleteMapping("/{email}")
	@ApiOperation(value = "Eliminar un login por Email", notes = "Servicio elimina Login a partir de un Email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login eliminado con exito"),
			@ApiResponse(code = 404, message = "Login no encontrado") })
	public ResponseEntity<Empty> eliminarLogin(@PathVariable("email") String email) {

		service.borrarLogin(email);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}

	@PutMapping("/")
	@ApiOperation(value = "Modificar un Login por Email", notes = "Servicio para modificar un Login a partir de un Email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login modificado correctamente"),
			@ApiResponse(code = 404, message = "Login no encontrado") })
	public ResponseEntity<Login> updateLogin(@RequestBody LoginVo loginVo) {

		return new ResponseEntity<>(service.actualizarLogin(loginVo), HttpStatus.OK);
	}

}
