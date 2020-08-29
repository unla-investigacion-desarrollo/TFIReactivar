package com.unla.reactivar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class DtoXUnidadVo {

	private Date fechaInicio;
	private Date fechaFin;
	private boolean habilitada;
	private String descripcion;
	private double porcDescuento;
	private int cantidad;
	private long idEmprendimiento;
	
}
