package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Localidad;
import com.unla.reactivar.models.Provincia;
import com.unla.reactivar.repositories.LocalidadRepository;
import com.unla.reactivar.vo.LocalidadVo;

@Service
@Transactional(readOnly = true)
public class LocalidadService {

	@Autowired
	private LocalidadRepository repository;
	
	@Autowired
	private ProvinciaService provinciaService;
	
	public Localidad traerLocalidadPorId(Long id) {
		return repository.findByIdLocalidad(id);
	}
	
	public List<Localidad> traerTodos(){
		return repository.findAll();
	}
	
	@Transactional
	public void borrarLocalidad(long id) {
		Localidad localidad = repository.findByIdLocalidad(id);
		
		if(localidad == null) {
			throw new ObjectNotFound("Localidad");
		}
		
		repository.delete(localidad);
	}
	@Transactional
	public Localidad actualizarLocalidad(Long id, LocalidadVo localidadVo) {
		Localidad localidad = repository.findByIdLocalidad(id);
		
		if(localidad == null) {
			throw new ObjectNotFound("Localidad");
		}
		
		adaptVoToLocalidad(localidad, localidadVo);
		
		return repository.save(localidad);
	}

	@Transactional
	public Localidad crearLocalidad(LocalidadVo localidadVo) {
		Localidad localidad = new Localidad();
		
		adaptVoToLocalidad(localidad, localidadVo);
		
		return repository.save(localidad);
	}
	

	private void adaptVoToLocalidad(Localidad localidad, LocalidadVo localidadVo) {
		Provincia provincia = provinciaService.traerProvinciaPorId(localidadVo.getIdProvincia());
		
		if(provincia == null) {
			throw new ObjectNotFound("Provincia");
		}
		
		localidad.setProvincia(provincia);
		localidad.setNombre(localidadVo.getLocalidad());
	}

	public List<Localidad> traerLocalidadesPorProvincia(Long idProvincia) {		
		return repository.findAllByProvincia(idProvincia);
	}
}
