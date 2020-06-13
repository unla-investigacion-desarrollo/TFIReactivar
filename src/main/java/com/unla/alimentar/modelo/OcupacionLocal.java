/**
 * 
 */
package com.unla.alimentar.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idLocal")
	@JsonBackReference
	private Local local;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idUsuario")
	@JsonBackReference
	private Usuario usuario;
	private Date fechaHoraEntrada;
	private Date fechaHoraSalida;
	
}
