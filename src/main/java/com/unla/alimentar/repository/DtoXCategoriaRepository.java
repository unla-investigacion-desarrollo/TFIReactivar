package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.DtoXCategoria;

@Repository
public interface DtoXCategoriaRepository extends JpaRepository<DtoXCategoria, Long>{
	
	public DtoXCategoria findByIdDtoXCategoria(Long idDtoXCategoria);
	
}
