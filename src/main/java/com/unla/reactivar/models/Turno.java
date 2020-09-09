/**
 * 
 */
package com.unla.reactivar.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/**
 * @author Matias
 *
 */
@Data
@Entity
@Table(name = "turno")
public class Turno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTurno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonManagedReference
	private Emprendimiento emprendimiento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoTurno", nullable = false)
	@JsonManagedReference
	private EstadoTurno estadoTurno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersona", nullable = false)
	@JsonManagedReference
	private Persona persona;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaHora;
	private String observaciones;
	private String usuarioModi;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaModi;

}
