package com.unla.alimentar.vo;

import lombok.Data;

@Data
public class PersonaFisicaVo {

	private String nombre;
	private String apellido;
	private String cuil; 
	private String celular;
	private String usuarioModi;
	private long idPerfil;
	private UbicacionVo ubicacionVo;
	private LoginVo loginVo;
	
}
