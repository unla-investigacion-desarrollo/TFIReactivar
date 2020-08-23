package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Emprendimiento;
import com.unla.alimentar.models.LlevaPaga;
import com.unla.alimentar.repositories.LlevaPagaRepository;
import com.unla.alimentar.vo.LlevaPagaVo;

@Service
@Transactional(readOnly = true)
public class LlevaPagaService {

	@Autowired
	private LlevaPagaRepository repository;
	
	@Autowired
	private EmprendimientoService emprendimientoService;

	public LlevaPaga traerLlevaPagaPorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<LlevaPaga> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarLlevaPaga(long id) {
		LlevaPaga registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("LlevaPaga");
		}

		repository.delete(registro);
	}

	@Transactional
	public LlevaPaga actualizarLlevaPaga(Long id, LlevaPagaVo llevaPagaVo) {
		LlevaPaga registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("LlevaPaga");
		}
		
		adaptVoToDtoXPorcentaje(registro, llevaPagaVo);
		
		return repository.save(registro);
	}

	@Transactional
	public LlevaPaga crearLlevaPaga(LlevaPagaVo llevaPagaVo) {
		LlevaPaga dto = new LlevaPaga();
		
		adaptVoToDtoXPorcentaje(dto, llevaPagaVo);
		
		return repository.save(dto);
	}
	
	private void adaptVoToDtoXPorcentaje(LlevaPaga dto, LlevaPagaVo llevaPagaVo) {
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(llevaPagaVo.getIdEmprendimiento());

		if (emprendimiento == null) {
			throw new ObjectNotFound("Emprendimiento");
		}

		dto.setDescripcion(llevaPagaVo.getDescripcion());
		dto.setHabilitada(llevaPagaVo.isHabilitada());
		dto.setFechaFin(llevaPagaVo.getFechaFin());
		dto.setFechaInicio(llevaPagaVo.getFechaInicio());
		dto.setEmprendimiento(emprendimiento);
		dto.setLleva(llevaPagaVo.getLleva());
		dto.setPaga(llevaPagaVo.getPaga());
	}

}
