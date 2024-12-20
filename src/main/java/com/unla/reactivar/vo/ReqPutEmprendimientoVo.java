package com.unla.reactivar.vo;

import java.util.List;

import lombok.Data;

@Data
public class ReqPutEmprendimientoVo {

	private String nombre;
	private String cuit;
	private String usuarioModi;
	private long idTipoEmprendimiento;
	private long idRubro;
	private long idPersona;
	private int capacidad;
	private long idEstadoEmprendimiento;
	private boolean aceptaFoto;
	private long telefono;
	private UbicacionVo ubicacionVo;
	private List<ConfiguracionLocalVo> configuracionLocales;
}
