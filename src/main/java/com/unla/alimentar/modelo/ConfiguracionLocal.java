package com.unla.alimentar.modelo;

import java.sql.Time;
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

@Data
@Entity
@Table(name = "configuracionLocal")
public class ConfiguracionLocal {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idConfiguracionLocal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonBackReference
	private Emprendimiento emprendimiento;
	
	private String diaSemana;
	private Time turno1Desde;
	private Time turno1Hasta;
	private Time turno2Desde;
	private Time turno2Hasta;
	private int intervaloTurnos;
	private String usuarioModi;
	private Date fechaModi;

}
