/**
 * 
 */
package com.unla.alimentar.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * @author Matias
 *
 */
@Data
@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String idUsuario;
	private int dni;
	private char sexo;
	private String usuario;
	private String password;
	private String usuarioModi;
	private Date fechaModi;
	private String calle;
	private int numeracion;
	private int codigoPostal;
	private String departamento;
	private Localidad localidad;
	private String latitud;
	private String longitud;
	private Perfil perfil;
	
}
