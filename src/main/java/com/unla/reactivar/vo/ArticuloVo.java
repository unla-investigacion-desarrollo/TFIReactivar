package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class ArticuloVo {

	private String codBarra;
	private String descripcion;
	private String nombre;
	private String foto;
	private double precio;
	private String usuarioModi;
	private String peso;
	private long idCategoria;
	private long idMarca;
	private long idUnidadMedida;
	private boolean activoComercial;
	private boolean visible;
	private long idEmprendimiento;
	private long idPromocion;
}
