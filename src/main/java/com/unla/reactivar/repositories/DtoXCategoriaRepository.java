package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.DtoXCategoria;

@Repository
public interface DtoXCategoriaRepository extends JpaRepository<DtoXCategoria, Long>{
	
	public DtoXCategoria findByIdPromocion(Long idDtoXCategoria);
	
}