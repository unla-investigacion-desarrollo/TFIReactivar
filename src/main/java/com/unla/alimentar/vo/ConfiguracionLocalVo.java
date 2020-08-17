package com.unla.alimentar.vo;


import java.sql.Time;

import lombok.Data;

@Data
public class ConfiguracionLocalVo {
	
	private String diaSemana;
	private String turno1Desde;
	private String turno1Hasta;
	private String turno2Desde;
	private String turno2Hasta;
	private int intervaloTurnos;
	private int tiempoAtencion;

}
