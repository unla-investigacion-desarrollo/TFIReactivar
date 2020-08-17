/**
 * 
 */
package com.unla.alimentar.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idPerfil;
	private String nombre;
	private String usuarioModi;
	private Date fechaModi;
	

	/*@OneToMany(mappedBy = "perfil")
	@JsonManagedReference
	private List<Usuario> usuarios;*/
	
	@OneToMany(mappedBy = "perfil")
	@JsonManagedReference
	private List<Persona> personas;

	@OneToMany(mappedBy = "perfil")
	@JsonManagedReference
	private List<FuncionPerfil> funcionesPerfil;
}
