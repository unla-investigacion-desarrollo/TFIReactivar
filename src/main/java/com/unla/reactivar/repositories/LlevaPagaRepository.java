package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.LlevaPaga;

@Repository
public interface LlevaPagaRepository extends JpaRepository<LlevaPaga, Long>{
		
	@Query("SELECT p FROM Promocion p WHERE p.idPromocion = ?1 and tipo_promocion = 'llevapaga'")
	public LlevaPaga findByIdPromocion(Long idLlevaPaga);
	
	@Modifying
	@Query("SELECT p FROM Promocion p WHERE tipo_promocion = 'llevapaga'")
	public List<LlevaPaga> findAll();
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Promocion p WHERE p.idPromocion = ?1")
	public void deletePromocion(Long idPromocion);
	
}
