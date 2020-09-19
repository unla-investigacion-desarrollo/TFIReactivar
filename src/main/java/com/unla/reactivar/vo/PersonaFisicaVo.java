package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class PersonaFisicaVo {

	private String nombre;
	private String apellido;
	private String sexo;
	private String cuil;
	private String celular;
	private String usuarioModi;
	private long idPerfil;
	private UbicacionVo ubicacionVo;
	private LoginVo loginVo;

}
