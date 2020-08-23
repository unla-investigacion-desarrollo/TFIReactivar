package com.unla.alimentar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PersonaJuridicaVo {

	private String celular;
	private String usuarioModi;
	private Date fechaModi;
	private long idPerfil;
	private UbicacionVo ubicacionVo;
	private LoginVo loginVo;
	private String razonSocial;
	private String cuit;

}
