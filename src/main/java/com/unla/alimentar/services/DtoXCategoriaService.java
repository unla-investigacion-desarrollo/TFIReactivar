package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Categoria;
import com.unla.alimentar.models.DtoXCategoria;
import com.unla.alimentar.models.Emprendimiento;
import com.unla.alimentar.repositories.DtoXCategoriaRepository;
import com.unla.alimentar.vo.DtoXCategoriaVo;

@Service
@Transactional(readOnly = true)
public class DtoXCategoriaService {

	@Autowired
	private DtoXCategoriaRepository repository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private EmprendimientoService emprendimientoService;

	public DtoXCategoria traerDtoXCategoriaPorId(Long id) {
		return repository.findByIdPromocion(id);
	}

	public List<DtoXCategoria> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXCategoria(long id) {
		DtoXCategoria registro = repository.findByIdPromocion(id);

		if (registro == null) {
			throw new ObjectNotFound("DtoXCategoria");
		}

		repository.delete(registro);
	}

	@Transactional
	public DtoXCategoria actualizarDtoXCategoria(Long id, DtoXCategoriaVo dtoXCategoriaVo) {
		DtoXCategoria dto = repository.findByIdPromocion(id);
		
		if(dto == null) {
			throw new ObjectNotFound("Descuento");
		}
		
		adaptVoToDtoXCategoria(dto, dtoXCategoriaVo);
		
		return repository.save(dto);
	}

	@Transactional
	public DtoXCategoria crearDtoXCategoria(DtoXCategoriaVo dtoXCategoriaVo) {
		DtoXCategoria dto = new DtoXCategoria();
		
		adaptVoToDtoXCategoria(dto, dtoXCategoriaVo);
		
		return repository.save(dto);
	}

	private void adaptVoToDtoXCategoria(DtoXCategoria dto, DtoXCategoriaVo dtoXCategoriaVo) {
		Categoria categoria = categoriaService.traerCategoriaPorId(dtoXCategoriaVo.getIdCategoria());
		Emprendimiento emprendimiento = emprendimientoService.traerEmprendimientoPorId(dtoXCategoriaVo.getIdEmprendimiento());
		
		if(categoria == null || emprendimiento == null) {
			throw new ObjectNotFound("Categoria / Emprendimiento");
		}
		
		dto.setCategoria(categoria);
		dto.setDescripcion(dtoXCategoriaVo.getDescripcion());
		dto.setDescuento(dtoXCategoriaVo.getDescuento());
		dto.setHabilitada(dtoXCategoriaVo.isHabilitada());
		dto.setFechaFin(dtoXCategoriaVo.getFechaFin());
		dto.setFechaInicio(dtoXCategoriaVo.getFechaInicio());
		dto.setEmprendimiento(emprendimiento);
	}
}
