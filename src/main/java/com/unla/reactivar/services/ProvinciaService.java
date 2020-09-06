package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Localidad;
import com.unla.reactivar.models.Provincia;
import com.unla.reactivar.repositories.ProvinciaRepository;
import com.unla.reactivar.vo.ProvinciaVo;

@Service
@Transactional(readOnly = true)
public class ProvinciaService {

	@Autowired
	private ProvinciaRepository repository;
	
	@Autowired
	private LocalidadService localidadService;
	
	public Provincia traerProvinciaPorId(Long id) {
		return repository.findByIdProvincia(id);
	}
	
	public List<Provincia> traerTodos(){
		return repository.findAll();
	}
	
	@Transactional
	public void borrarProvincia(long id) {
		Provincia provincia = repository.findByIdProvincia(id);
		
		if(provincia == null) {
			throw new ObjectNotFound("Provincia");
		}
		
		repository.delete(provincia);
	}

	@Transactional
	public Provincia actualizarProvincia(Long id, ProvinciaVo provinciaVo) {
		Provincia provincia = repository.findByIdProvincia(id);
		
		if(provincia == null) {
			throw new ObjectNotFound("Provincia");
		}
		
		provincia.setNombre(provinciaVo.getProvincia());
		
		return repository.save(provincia);
	}

	@Transactional
	public Provincia crearProvincia(ProvinciaVo provinciaVo) {
		Provincia provincia = new Provincia();
		
		provincia.setNombre(provinciaVo.getProvincia());
		
		return repository.save(provincia);
	}

	public List<Localidad> traerLocalidades(Long id) {		
		Provincia provincia = repository.findByIdProvincia(id);
		
		if(provincia == null) {
			throw new ObjectNotFound("Provincia");
		}
				
		return localidadService.traerLocalidadesPorProvincia(id);
	}
	
}
