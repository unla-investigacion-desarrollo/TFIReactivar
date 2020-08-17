package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long>{
	
	public Carrito findByIdCarrito(Long idCarrito);
	
}
