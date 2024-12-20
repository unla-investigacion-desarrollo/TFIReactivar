package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {

	public Emprendimiento findByIdEmprendimiento(Long id);

	@Query(value = "{call sp_mostrarPorRubro(:id_rubro)}", nativeQuery = true)
	List<Emprendimiento> traerPorRubro(@Param("id_rubro") long idRubro);

	@Query(value = "{call sp_emprendimientosCercanos(:id_rubro, :id_persona, :cantidadKm) }", nativeQuery = true)
	List<Emprendimiento> traerEmprendimientosCercanos(@Param("id_rubro") long idRubro,
			@Param("id_persona") long idPersona, @Param("cantidadKm") String cantidadKm);
	
	@Query(value = "{call sp_emprendimientosCercanosPosActual(:id_rubro, :latActual, :longActual, :cantidadKm) }", nativeQuery = true)
	List<Emprendimiento> traerEmprendimientosCercanosPosActual(@Param("id_rubro") long idRubro, @Param("latActual") String latActual, 
			@Param("longActual") String longitud, @Param("cantidadKm") String cantidadKm);


	@Query("SELECT e FROM Emprendimiento e WHERE e.estadoEmprendimiento=1")
	public List<Emprendimiento> findAllInactivos();

	@Query(value = "{SELECT cl FROM configuracion_local cl JOIN emprendimiento e WHERE cl.id_configuracion_local=(:id_emprendimiento)}", nativeQuery = true)
	List<ConfiguracionLocal> traerConfiguracionLocal(@Param("id_emprendimiento") long id);

	@Query("SELECT e FROM Emprendimiento e WHERE e.estadoEmprendimiento=2")
	public List<Emprendimiento> findAllActivos();

	@Query("SELECT e FROM Emprendimiento e WHERE e.estadoEmprendimiento=3")
	public List<Emprendimiento> findAllBajas();

	@Query("SELECT e FROM Emprendimiento e WHERE e.estadoEmprendimiento="
			+ "(SELECT ee.idEstadoEmprendimiento FROM EstadoEmprendimiento ee WHERE ee.idEstadoEmprendimiento= :estadoEmp)")
	public List<Emprendimiento> findAllEmprendimientoByEstado(@Param("estadoEmp") Long estadoEmp);

	@Query("select case when sum(cl.intervaloTurnos) > 0 then true else false end from ConfiguracionLocal cl where cl.emprendimiento=?1 group by cl.emprendimiento ")
	public boolean usaTurno(Emprendimiento emprendimiento);

}
