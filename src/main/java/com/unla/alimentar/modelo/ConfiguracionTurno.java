/**
 * 
 */
package com.unla.alimentar.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
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
@Table(name = "configuracionTurno")
public class ConfiguracionTurno implements Serializable {

	private static final long serialVersionUID = -3828728491551187571L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@OneToOne
	@MapsId("idLocal")
	private Local local;
	private int horaAtencionDesde;
	private int horaAtencionHasta;
	private int intervaloTurno;

}
