package com.unla.alimentar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class LlevaPagaVo {

	private Date fechaInicio;
	private Date fechaFin;
	private boolean habilitada;
	private String descripcion;
	private int lleva;
	private double paga;
	private long idEmprendimiento;

}
