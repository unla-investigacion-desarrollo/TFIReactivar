package com.unla.reactivar.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.Image;
import com.unla.reactivar.vo.ImageRepository;

@Service
@Transactional(readOnly = true)
public class ImageService {

	@Autowired
	private EmprendimientoService emprendimientoService;
	
	@Autowired
	private ImageRepository repository;
	
	@Value("${image.upload.directory}")
	private String imageUploadPath;
	
	@Transactional
	public Image crearImagen(String nombreImagen, long idEmprendimiento, String imageBase64) {
		Image image = new Image();
		
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(idEmprendimiento);
		
		if(emprendimiento == null)
			throw new ObjectNotFound("Emprendimiento");
		
		image.setEmprendimiento(emprendimiento);
		image.setNombreImagen(nombreImagen);
		image.setImagenBase64(imageBase64);
		
		return repository.save(image);
	}
	
	@Transactional
	public void borrarImagen(long idImagen) {
		Image imagen = repository.findByIdImage(idImagen);
		
		if(imagen == null)
			throw new ObjectNotFound("Image");
		
		File file = new File(imageUploadPath+"/"+imagen.getNombreImagen()+".jpg"); 
		
		file.delete();
		
		repository.delete(imagen);
	}

	public List<Image> traerImagenesPorEmprendimiento(long idEmprendimiento) {
		List<Image> imagenes = new ArrayList<>();
		
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(idEmprendimiento);
		
		if(emprendimiento == null)
			throw new ObjectNotFound("Emprendimiento");
		
		imagenes = repository.findByEmprendimiento(emprendimiento);
		
		return imagenes;
	}
}
