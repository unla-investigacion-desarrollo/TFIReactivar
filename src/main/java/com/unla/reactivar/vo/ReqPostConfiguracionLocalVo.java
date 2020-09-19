package com.unla.reactivar.vo;


import lombok.Data;

@Data
public class ReqPostConfiguracionLocalVo {
	
	private String diaSemana;
	private String turno1Desde;
	private String turno1Hasta;
	private String turno2Desde;
	private String turno2Hasta;
	private int intervaloTurnos;
	private int tiempoAtencion;
	private String usuarioModi;
	private long idEmprendimiento;

}
