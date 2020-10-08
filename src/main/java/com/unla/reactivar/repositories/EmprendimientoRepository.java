package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.Emprendimiento;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {

	public Emprendimiento findByIdEmprendimiento(Long id);

	@Query(value = "{call sp_mostrarPorRubro(:id_rubro)}", nativeQuery = true)
	List<Emprendimiento> traerPorRubro(@Param("id_rubro") long idRubro);

	@Query(value = "{call sp_emprendimientosCercanos(:id_rubro, :id_persona, :cantidadKm) }", nativeQuery = true)
	List<Emprendimiento> traerEmprendimientosCercanos(@Param("id_rubro") long idRubro,
			@Param("id_persona") long idPersona, @Param("cantidadKm") String cantidadKm);

	@Query("SELECT e FROM Emprendimiento e WHERE e.estadoEmprendimiento=1")
	public List<Emprendimiento> findAllInactivos();

	@Query("SELECT e FROM Emprendimiento e WHERE e.estadoEmprendimiento=2")
	public List<Emprendimiento> findAllActivos();

	@Query("SELECT e FROM Emprendimiento e WHERE e.estadoEmprendimiento=3")
	public List<Emprendimiento> findAllBajas();

	@Query("SELECT e FROM Emprendimiento e WHERE e.estadoEmprendimiento="
			+ "(SELECT ee.idEstadoEmprendimiento FROM EstadoEmprendimiento ee WHERE ee.idEstadoEmprendimiento= :estadoEmp)")
	public List<Emprendimiento> findAllEmprendimientoByEstado(@Param("estadoEmp") Long estadoEmp);

	

}
