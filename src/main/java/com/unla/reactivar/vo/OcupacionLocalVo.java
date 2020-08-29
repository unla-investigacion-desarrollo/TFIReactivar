package com.unla.reactivar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OcupacionLocalVo {

	private long idPersona;
	private Date fechaHoraEntrada;
	private Date fechaHoraSalida;
	private String usuarioModi;
	private long idEmprendimiento;
	
}
