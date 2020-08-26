package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class ArticuloReferenciaVo {

	private String codBarra;
	private String descripcion;
	private String nombre;
	private String foto;
	private double precioRefencia;
	private String usuarioModi;
	private String peso;
	private long idCategoria;
	private long idMarca;
	private long idUnidadMedida;
	
}
