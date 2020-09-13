package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class ItemCarritoVo {

	private long idArticulo;
	private int cantidad;

	public ItemCarritoVo(long idArticulo, int cantidad) {
		super();
		this.idArticulo = idArticulo;
		this.cantidad = cantidad;
	}

}
