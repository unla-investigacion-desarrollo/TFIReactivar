package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

	public Carrito findByIdCarrito(Long idCarrito);

}
