package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class PersonaJuridicaVo {

	private String celular;
	private String usuarioModi;
	private long idPerfil;
	private UbicacionVo ubicacionVo;
	private LoginVo loginVo;
	private String razonSocial;
	private String cuit;

}
