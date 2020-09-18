package com.unla.reactivar.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CoordenadasVo implements Serializable {

	private static final long serialVersionUID = -5970105598700418631L;

	private String latitud;
	private String longitud;

	public CoordenadasVo(String latitud, String longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}

}
