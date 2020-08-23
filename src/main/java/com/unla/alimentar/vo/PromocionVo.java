package com.unla.alimentar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PromocionVo {
	
	private Date fechaInicio;
	private Date fechaFin;
	private boolean habilitada;
	private String descripcion;

}
