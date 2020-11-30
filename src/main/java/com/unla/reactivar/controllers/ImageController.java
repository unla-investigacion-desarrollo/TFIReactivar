package com.unla.reactivar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.reactivar.models.Image;
import com.unla.reactivar.services.ImageService;
import com.unla.reactivar.vo.Empty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/image")
@Api(tags = "Image")
@CrossOrigin(origins = "*")
public class ImageController {

	@Autowired
	private ImageService service;
	
	@DeleteMapping("/{idImage}")
	@ApiOperation(value = "Eliminar image", notes = "Servicio elimina Image")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Image eliminado con exito"),
			@ApiResponse(code = 404, message = "Image no encontrado") })
	public ResponseEntity<Empty> eliminarImage(@PathVariable("idImage") long id) {

		service.borrarImagen(id);

		return new ResponseEntity<>(new Empty(), HttpStatus.OK);
	}
	
	@GetMapping("/{idEmprendimiento}")
	public ResponseEntity<List<Image>> traerImagenesPorEmprendimiento(@PathVariable("idEmprendimiento") long idEmprendimiento){
		return new ResponseEntity<>(service.traerImagenesPorEmprendimiento(idEmprendimiento), HttpStatus.OK);
	}
}
