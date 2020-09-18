package com.unla.reactivar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class TurnoVo {

	private long idEmprendimiento;
	private long idEstadoTurno;
	private long idPersona;
	private Date fechaHora;
	private String observaciones;
	private String usuarioModi;

}
