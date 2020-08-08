/**
 * 
 */
package com.unla.alimentar.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.alimentar.modelo.Usuario;
import com.unla.alimentar.service.UsuarioService;

/**
 * @author Matias
 *
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<Usuario> login(@RequestParam("usuario") String username, @RequestParam("password") String pwd) {

		Usuario user = usuarioService.traerUsuarioPorUsuario(username);
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(!pwd.equals(user.getPassword())){
			//Lanzar Exception
		}
				
		return new ResponseEntity<>(user, HttpStatus.OK);

	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> traerTodos(){
		return new ResponseEntity<>(usuarioService.traerTodos(), HttpStatus.OK);
	}

}
