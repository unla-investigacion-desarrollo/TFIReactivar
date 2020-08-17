package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.Articulo;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long>{
	
	public Articulo findByIdArticulo(Long idArticulo);
	
}
