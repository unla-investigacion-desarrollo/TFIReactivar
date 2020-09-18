package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class ReqPutPersonaFisicaVo {

	private String nombre;
	private String apellido;
	private String cuil;
	private String celular;
	private String usuarioModi;
	private long idPerfil;

}
