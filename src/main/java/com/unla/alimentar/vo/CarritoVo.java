package com.unla.alimentar.vo;

import java.util.List;

import lombok.Data;

@Data
public class CarritoVo {

	private long idEmprendimiento;
	private long idPersona;
	private long idEstadoCarrito;
	private List<ItemCarritoVo> listaItemCarrito;
	
}
