package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Localidad;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

	public Localidad findByIdLocalidad(Long id);

	@Query("SELECT l FROM Localidad l WHERE id_provincia = ?1")
	public List<Localidad> findAllByProvincia(Long idProvincia);

}
