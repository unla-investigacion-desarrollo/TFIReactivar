package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long>{
	
	public Promocion findByIdPromocion(Long idPromocion);
	
}
