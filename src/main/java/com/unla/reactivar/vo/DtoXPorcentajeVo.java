package com.unla.reactivar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class DtoXPorcentajeVo {

	private Date fechaInicio;
	private Date fechaFin;
	private boolean habilitada;
	private String descripcion;
	private double descuento;
	private long idEmprendimiento;

}
