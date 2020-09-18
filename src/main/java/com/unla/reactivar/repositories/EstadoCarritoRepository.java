package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.EstadoCarrito;

@Repository
public interface EstadoCarritoRepository extends JpaRepository<EstadoCarrito, Long> {

	public EstadoCarrito findByIdEstadoCarrito(Long idEstadoCarrito);

}
