package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {

	@Query("SELECT p FROM Promocion p WHERE p.idPromocion = ?1")
	public Promocion findByIdPromocion(Long idLlevaPaga);

	@Modifying
	@Query("SELECT p FROM Promocion p")
	public List<Promocion> findAll();

	@Transactional
	@Modifying
	@Query("DELETE FROM Promocion p WHERE p.idPromocion = ?1")
	public void deletePromocion(Long idPromocion);

}
