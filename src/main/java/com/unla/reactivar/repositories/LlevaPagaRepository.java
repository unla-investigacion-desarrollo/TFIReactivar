package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.LlevaPaga;

@Repository
public interface LlevaPagaRepository extends JpaRepository<LlevaPaga, Long>{
	
	public LlevaPaga findByIdPromocion(Long idLlevaPaga);
	
}
