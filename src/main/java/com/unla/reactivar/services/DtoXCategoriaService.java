package com.unla.reactivar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Categoria;
import com.unla.reactivar.models.DtoXCategoria;
import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.repositories.DtoXCategoriaRepository;
import com.unla.reactivar.vo.DtoXCategoriaVo;

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

		repository.deletePromocion(id);
	}

	@Transactional
	public DtoXCategoria actualizarDtoXCategoria(Long id, DtoXCategoriaVo dtoXCategoriaVo) {
		DtoXCategoria dto = repository.findByIdPromocion(id);

		if (dto == null) {
			throw new ObjectNotFound("Descuento");
		}

		adaptVoToDtoXCategoria(dto, dtoXCategoriaVo);

		try {
			dto = repository.save(dto);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return dto;
	}

	@Transactional
	public DtoXCategoria crearDtoXCategoria(DtoXCategoriaVo dtoXCategoriaVo) {
		DtoXCategoria dto = new DtoXCategoria();

		adaptVoToDtoXCategoria(dto, dtoXCategoriaVo);

		try {
			dto = repository.save(dto);
		} catch (Exception e) {
			throw new ObjectAlreadyExists();
		}

		return dto;
	}

	private void adaptVoToDtoXCategoria(DtoXCategoria dto, DtoXCategoriaVo dtoXCategoriaVo) {
		Categoria categoria = categoriaService.traerCategoriaPorId(dtoXCategoriaVo.getIdCategoria());
		Emprendimiento emprendimiento = emprendimientoService
				.traerEmprendimientoPorId(dtoXCategoriaVo.getIdEmprendimiento());

		if (categoria == null || emprendimiento == null) {
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
