package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.DtoXUnidad;

@Repository
public interface DtoXUnidadRepository extends JpaRepository<DtoXUnidad, Long>{
	
	public DtoXUnidad findByIdPromocion(Long idDtoXUnidad);
	
}
