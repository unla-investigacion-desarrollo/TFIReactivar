package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long>{
	
	public Promocion findByIdPromocion(Long idPromocion);
	
}
