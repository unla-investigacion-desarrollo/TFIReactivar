package com.unla.alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.alimentar.modelo.Localidad;
import com.unla.alimentar.modelo.Ubicacion;
import com.unla.alimentar.repository.UbicacionRepository;
import com.unla.alimentar.vo.UbicacionVo;

@Service
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
		if(ubicacionVo.getDepartamento() != null)
			ubicacion.setDepartamento(ubicacionVo.getDepartamento());
		if(new Integer(ubicacionVo.getPiso()) != null || ubicacionVo.getPiso() != 0)
			ubicacion.setPiso(ubicacionVo.getPiso());
		ubicacion.setLatitud(ubicacionVo.getLatitud());
		ubicacion.setLongitud(ubicacionVo.getLongitud());
		Localidad localidad = localidadService.traerLocalidadPorId(ubicacionVo.getIdLocalidad());
		if(localidad == null) {
			//lanzar exception
		}
		ubicacion.setLocalidad(localidad);
		
		return ubicacion;
	}
	
}
