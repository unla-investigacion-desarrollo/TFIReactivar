package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.EstadoCarrito;

@Repository
public interface EstadoCarritoRepository extends JpaRepository<EstadoCarrito, Long>{
	
	public EstadoCarrito findByIdEstadoCarrito(Long idEstadoCarrito);
	
}
