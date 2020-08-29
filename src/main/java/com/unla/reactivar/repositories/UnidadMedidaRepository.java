package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.UnidadMedida;

@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long>{
	
	public UnidadMedida findByIdUnidadMedida(Long idUnidadMedida);
	
}
