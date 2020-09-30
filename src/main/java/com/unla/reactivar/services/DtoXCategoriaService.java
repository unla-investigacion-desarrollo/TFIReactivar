package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private DtoXCategoriaRepository repository;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private EmprendimientoService emprendimientoService;

	public DtoXCategoria traerDtoXCategoriaPorId(Long id) {
		log.info("Se traera un DtoXCategoria por id");
		return repository.findByIdPromocion(id);
	}

	public List<DtoXCategoria> traerTodosDtosXCategorias() {
		log.info("Se traeran todos los dtos por categoria");
		return repository.findAll();
	}

	@Transactional
	public void borrarDtoXCategoria(long id) {
		DtoXCategoria registro = repository.findByIdPromocion(id);

		if (registro == null) {
			log.error("Se obtuvo un error al intentar borrar el dto x categoria [{}]", id);
			throw new ObjectNotFound("DtoXCategoria");
		}

		log.info("Se eliminara promocion [{}]", registro.getIdPromocion());
		repository.deletePromocion(id);
	}

	@Transactional
	public DtoXCategoria actualizarDtoXCategoria(Long id, DtoXCategoriaVo dtoXCategoriaVo) {
		DtoXCategoria dto = repository.findByIdPromocion(id);

		if (dto == null) {
			log.error("Se obtuvo un error al intentar actualizar el dto [{}]", id);
			throw new ObjectNotFound("Descuento");
		}

		adaptVoToDtoXCategoria(dto, dtoXCategoriaVo);

		try {
			log.info("Se actualizara dto x categoria");
			dto = repository.save(dto);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return dto;
	}

	@Transactional
	public DtoXCategoria crearDtoXCategoria(DtoXCategoriaVo dtoXCategoriaVo) {
		DtoXCategoria dto = new DtoXCategoria();

		adaptVoToDtoXCategoria(dto, dtoXCategoriaVo);

		try {
			log.info("Se creara dto x categoria");
			dto = repository.save(dto);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
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
