/**
 * 
 */
package com.unla.alimentar.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "turno")
public class Turno {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String idTurno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonBackReference
	private Emprendimiento emprendimiento;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoTurno", nullable = false)
	@JsonBackReference
	private EstadoTurno estadoTurno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersona", nullable = false)
	@JsonBackReference
	private Persona persona;
	
	private Date fechaHora;
	private String observaciones;
	private String usuarioModi;
	private Date fechaModi;

	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idLocal", nullable = false)
	@JsonBackReference
	private Local local;*/

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstado", nullable = false)
	@JsonBackReference
	private Estado estado;*/
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario", nullable = false)
	@JsonBackReference
	private Usuario usuario;*/
}
