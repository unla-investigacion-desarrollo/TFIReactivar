package com.unla.alimentar.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.alimentar.modelo.Rubro;
import com.unla.alimentar.service.RubroService;

@RestController
@RequestMapping("/rubro")
public class RubroController {
	
	@Autowired
	private RubroService rubroService;
	
	@GetMapping("/{idRubro}")
	public ResponseEntity<Rubro> traerRubroPorId(@PathVariable("idRubro") String id){
		Rubro rubro = rubroService.traerRubroPorId(id);
		
		return new ResponseEntity<>(rubro, HttpStatus.OK);
	}

}
