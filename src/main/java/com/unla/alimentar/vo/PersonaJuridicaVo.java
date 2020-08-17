package com.unla.alimentar.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonaJuridicaVo{
	
	private String celular;
	private String razonSocial;
	private String cuit; 
	private	Long idLogin;
	private Long idPerfil;

}
