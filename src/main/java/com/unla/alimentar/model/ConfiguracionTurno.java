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
@Table(name = "configuracionTurno")
public class ConfiguracionTurno {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String idLocal;
	private int horaAtencionDesde;
	private int horaAtencionHasta;
	private int intervaloTurno;
	
}
