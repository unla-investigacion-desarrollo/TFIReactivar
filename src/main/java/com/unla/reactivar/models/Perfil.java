/**
 * 
 */
package com.unla.reactivar.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Matias
 *
 */
@Data
@Entity
@Table(name = "perfil")
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPerfil;
	@Column(unique = true)
	private String nombre;
	private String usuarioModi;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaModi;

	@OneToMany(mappedBy = "perfil")
	@JsonBackReference
	private List<Persona> personas;

	@OneToMany(mappedBy = "perfil")
	@JsonBackReference
	private List<FuncionPerfil> funcionesPerfil;
}
