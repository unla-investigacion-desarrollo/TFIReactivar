/**
 * 
 */
package com.unla.alimentar.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 * @author Matias
 *
 */
@Data
@Entity
@Table(name = "ocupacionLocal")
public class OcupacionLocal {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String idOcupacionLocal;
	@ManyToOne
	@JoinColumn(name = "ocupacionLocal")
	@JsonBackReference
	private Local local;
	private Usuario usuario;
	private Date fechaHoraEntrada;
	private Date fechaHoraSalida;
	
}
