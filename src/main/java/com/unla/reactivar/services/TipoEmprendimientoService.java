package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.TipoEmprendimiento;
import com.unla.reactivar.repositories.TipoEmprendimientoRepository;
import com.unla.reactivar.vo.TipoEmprendimientoVo;

@Service
@Transactional(readOnly = true)
public class TipoEmprendimientoService {

	@Autowired
	private TipoEmprendimientoRepository repository;
	
	public TipoEmprendimiento traerTipoEmprendimientoPorId(Long id) {
		return repository.findByIdTipoEmprendimiento(id);
	}
	
	public List<TipoEmprendimiento> traerTodosTiposEmprendimientos(){
		return repository.findAll();
	}
	
	@Transactional
	public void borrarTipoEmprendimiento(long id) {
		TipoEmprendimiento tipoEmprendimiento = repository.findByIdTipoEmprendimiento(id);
		
		if(tipoEmprendimiento == null) {
			throw new ObjectNotFound("TipoEmprendimiento");
		}
		
		repository.delete(tipoEmprendimiento);
	}

	@Transactional
	public TipoEmprendimiento actualizarTipoEmprendimiento(Long id, TipoEmprendimientoVo tipoEmprendimientoVo) {
		TipoEmprendimiento tipoEmprendimiento = repository.findByIdTipoEmprendimiento(id);
		
		if(tipoEmprendimiento == null) {
			throw new ObjectNotFound("TipoEmprendimiento");
		}
		
		tipoEmprendimiento.setNombre(tipoEmprendimientoVo.getTipoEmprendimiento());
		
		try {
			tipoEmprendimiento = repository.save(tipoEmprendimiento);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return tipoEmprendimiento;
	}

	@Transactional
	public TipoEmprendimiento crearTipoEmprendimiento(TipoEmprendimientoVo tipoEmprendimientoVo) {
		TipoEmprendimiento tipoEmprendimiento = new TipoEmprendimiento();
		
		tipoEmprendimiento.setNombre(tipoEmprendimientoVo.getTipoEmprendimiento());
		
		try {
			tipoEmprendimiento = repository.save(tipoEmprendimiento);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return tipoEmprendimiento;
	}
	
}
