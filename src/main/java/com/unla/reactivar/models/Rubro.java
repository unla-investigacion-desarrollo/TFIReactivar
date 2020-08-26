/**
 * 
 */
package com.unla.reactivar.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@JsonBackReference
	private List<Emprendimiento> emprendimientos;
	
}
