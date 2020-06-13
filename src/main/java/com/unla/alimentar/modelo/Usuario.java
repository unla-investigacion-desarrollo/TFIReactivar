/**
 * 
 */
package com.unla.alimentar.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idLocalidad")
	@JsonBackReference
	private Localidad localidad;
	private String latitud;
	private String longitud;
	private Perfil perfil;
	
	@OneToOne
	@MapsId("idLocal")
	private Local local;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OcupacionLocal> ocupacionLocales;
}
