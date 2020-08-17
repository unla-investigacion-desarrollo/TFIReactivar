package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.DtoXPorcentaje;

@Repository
public interface DtoXPorcentajeRepository extends JpaRepository<DtoXPorcentaje, Long>{
	
	public DtoXPorcentaje findByIdDtoXPorcentaje(Long idDtoXPorcentaje);
	
}
