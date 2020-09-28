package com.unla.reactivar.vo;

import com.unla.reactivar.models.Funcion;

import lombok.Data;

@Data
public class EndpointVo {

	private String nombreController;
	private String metodo;
	private String endpoint;
	private String descripcion;
	private FuncionVo Funcion;

}
