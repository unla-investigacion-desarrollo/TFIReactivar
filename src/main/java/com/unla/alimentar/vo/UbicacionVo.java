package com.unla.alimentar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class UbicacionVo {
	
	private String calleNumero;
	private int piso;
	private String departamento;
	private String latitud;
	private String longitud;
	private String usuarioModi;
	private Date fechaModi;
	private Long idLocalidad;
	private Long idProvincia;

}