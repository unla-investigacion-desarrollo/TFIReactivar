package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class UbicacionVo {
	
	private String calle;
	private int numero;
	private int piso;
	private String departamento;
	private String latitud;
	private String longitud;
	private String usuarioModi;
	private Long idLocalidad;
	private Long idProvincia;

}
