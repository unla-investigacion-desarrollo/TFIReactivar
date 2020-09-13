package com.unla.reactivar.vo;

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

}
