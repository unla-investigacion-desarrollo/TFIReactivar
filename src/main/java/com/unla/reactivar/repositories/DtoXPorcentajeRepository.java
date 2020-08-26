package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.DtoXPorcentaje;

@Repository
public interface DtoXPorcentajeRepository extends JpaRepository<DtoXPorcentaje, Long>{
	
	public DtoXPorcentaje findByIdPromocion(Long idDtoXPorcentaje);
	
}
