package com.unla.reactivar.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.OcupacionLocal;

@Repository
public interface OcupacionLocalRepository extends JpaRepository<OcupacionLocal, Long> {

	public OcupacionLocal findByIdOcupacionLocal(Long idOcupacionLocal);

	@Query("SELECT o FROM OcupacionLocal o WHERE id_emprendimiento = ?1 AND id_persona = ?2 AND fecha_hora_salida = null")
	public OcupacionLocal findByEmprendimientoPersona(Long idEmprendimiento, Long idPersona);

	@Query("SELECT o FROM OcupacionLocal o WHERE fechaHoraEntrada BETWEEN ?1 and ?2")
	public List<OcupacionLocal> findByFechaDesdeHasta(Date fechaInicio, Date fechaFin);

	public List<OcupacionLocal> findByFechaHoraSalidaIsNull();

	@Query("select count(*) from OcupacionLocal where id_emprendimiento = ?1  and fecha_hora_salida = null")
	public int traerCantidadClientes(Long idEmprendimiento);

}
