/**
 * 
 */
package com.unla.alimentar.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String idRubro;
	private String nombre;
	
	@OneToOne(mappedBy = "rubro", cascade = CascadeType.ALL)
	private Local local;
	
}
