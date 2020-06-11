/**
 * 
 */
package com.unla.alimentar.model;

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
@Table(name = "perfil")
public class Perfil {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String idPerfil;
	private String nombre;
	
}
