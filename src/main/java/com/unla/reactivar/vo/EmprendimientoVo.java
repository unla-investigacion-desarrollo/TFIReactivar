package com.unla.reactivar.vo;

import java.util.List;

import lombok.Data;

@Data
public class EmprendimientoVo {

	private String nombre;
	private String cuit;
	private String usuarioModi;
	private long idTipoEmprendimiento;
	private UbicacionVo ubicacionVo;
	private long idRubro;
	private long idPersona;
	private List<ConfiguracionLocalVo> configuracionLocales;
	private int capacidad;

}
