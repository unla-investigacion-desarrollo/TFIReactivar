package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.ReqArticulo;

@Repository
public interface ArticuloRepository extends JpaRepository<ReqArticulo, Long>{
	
	public ReqArticulo findByIdArticulo(Long idArticulo);
	
}
