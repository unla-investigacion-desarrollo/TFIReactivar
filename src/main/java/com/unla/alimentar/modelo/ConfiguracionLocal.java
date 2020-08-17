package com.unla.alimentar.modelo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "configuracionLocal")
public class ConfiguracionLocal {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idConfiguracionLocal;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonBackReference
	private Emprendimiento emprendimiento;
	
	private String diaSemana;
	private String turno1Desde;
	private String turno1Hasta;
	private String turno2Desde;
	private String turno2Hasta;
	private int intervaloTurnos;
	private int tiempoAtencion;
	private String usuarioModi;
	private Date fechaModi;

}
