package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.DtoXUnidad;

@Repository
public interface DtoXUnidadRepository extends JpaRepository<DtoXUnidad, Long>{
	
	public DtoXUnidad findByIdDtoXUnidad(Long idDtoXUnidad);
	
}
