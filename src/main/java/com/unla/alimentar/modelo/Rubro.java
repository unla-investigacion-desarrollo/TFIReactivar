/**
 * 
 */
package com.unla.alimentar.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/**
 * @author Matias
 *
 */
@Data
@Entity
@Table(name = "rubro")
public class Rubro {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idRubro;
	private String nombre;



	@OneToMany(mappedBy = "rubro")
	@JsonManagedReference
	private List<Emprendimiento> emprendimientos;
	
	
	/*@OneToMany(mappedBy = "rubro")
	@JsonManagedReference
	private List<Local> locales;*/
}
