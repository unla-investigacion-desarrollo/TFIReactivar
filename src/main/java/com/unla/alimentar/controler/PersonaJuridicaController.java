package com.unla.alimentar.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unla.alimentar.modelo.Persona;
import com.unla.alimentar.service.PersonaJuridicaService;
import com.unla.alimentar.vo.PersonaJuridicaVo;

@RestController
@RequestMapping("/juridica")
public class PersonaJuridicaController {
	
	@Autowired
	private PersonaJuridicaService personaJuridicaService;
	
	@GetMapping("/{idJuridica}")
	public ResponseEntity<Persona> traerRubroPorId(@PathVariable("idPersona") long idPersona){
		Persona persona = personaJuridicaService.traerPersonaPorId(idPersona);
		
		return new ResponseEntity<>(persona, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Persona> crearPersona(@RequestBody PersonaJuridicaVo personaJuridica){
		
		Persona juridica = personaJuridicaService.crearPersona(personaJuridica);
		
		return new ResponseEntity<>(juridica, HttpStatus.CREATED);
	}

}