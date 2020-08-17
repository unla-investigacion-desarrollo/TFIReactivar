package com.unla.alimentar.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exception.ObjectNotFound;
import com.unla.alimentar.modelo.Localidad;
import com.unla.alimentar.modelo.Ubicacion;
import com.unla.alimentar.repository.UbicacionRepository;
import com.unla.alimentar.vo.UbicacionVo;

@Service
@Transactional(readOnly = true)
public class UbicacionService {

	@Autowired
	private UbicacionRepository repository;

	@Autowired
	private LocalidadService localidadService;
		
	
	public Ubicacion traerUbicacionPorId(Long id) {
		return repository.findByIdUbicacion(id);
	}
	
	public Ubicacion crearUbicacion(UbicacionVo ubicacionVo) {
		Ubicacion ubicacion = new Ubicacion();
		
		ubicacion.setCalleNumero(ubicacionVo.getCalleNumero());
		if(!StringUtils.isBlank(ubicacionVo.getDepartamento()))
			ubicacion.setDepartamento(ubicacionVo.getDepartamento());
		if(new Integer(ubicacionVo.getPiso()) != null || ubicacionVo.getPiso() != 0)
			ubicacion.setPiso(ubicacionVo.getPiso());
		ubicacion.setLatitud(ubicacionVo.getLatitud());
		ubicacion.setLongitud(ubicacionVo.getLongitud());
		Localidad localidad = localidadService.traerLocalidadPorId(ubicacionVo.getIdLocalidad());
		if(localidad == null) {
			throw new ObjectNotFound("Localidad");
		}
		ubicacion.setLocalidad(localidad);
		
		return ubicacion;
	}
	
	public List<Ubicacion> traerTodos(){
		return repository.findAll();
	}
	
	@Transactional
	public void borrarUbicacion(long id) {
		Ubicacion ubicacion = repository.findByIdUbicacion(id);
		
		if(ubicacion == null) {
			throw new ObjectNotFound("Ubicacion");
		}
		
		repository.delete(ubicacion);
	}
	
}
