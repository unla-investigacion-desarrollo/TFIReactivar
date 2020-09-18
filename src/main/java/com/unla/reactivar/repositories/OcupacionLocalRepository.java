package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.OcupacionLocal;

@Repository
public interface OcupacionLocalRepository extends JpaRepository<OcupacionLocal, Long> {

	public OcupacionLocal findByIdOcupacionLocal(Long idOcupacionLocal);

	@Query("SELECT o FROM OcupacionLocal o WHERE id_emprendimiento = ?1 AND id_persona = ?2 AND fecha_hora_salida = null")
	public OcupacionLocal findByEmprendimientoPersona(Long idEmprendimiento, Long idPersona);

}
