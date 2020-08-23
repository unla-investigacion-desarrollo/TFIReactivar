package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.DtoXPorcentaje;

@Repository
public interface DtoXPorcentajeRepository extends JpaRepository<DtoXPorcentaje, Long>{
	
	public DtoXPorcentaje findByIdPromocion(Long idDtoXPorcentaje);
	
}