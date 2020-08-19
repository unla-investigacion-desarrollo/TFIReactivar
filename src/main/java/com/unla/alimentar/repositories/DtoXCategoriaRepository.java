package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.Promocion;

@Repository
public interface DtoXCategoriaRepository extends JpaRepository<Promocion, Long>{
	
	public Promocion findByIdPromocion(Long idDtoXCategoria);
	
}
