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

@Data
@Entity
@Table(name = "estadoTurno")
public class EstadoTurno {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idEstadoTurno;
	private String estado;
	
	@OneToMany(mappedBy = "estadoTurno")
	@JsonBackReference
	private List<Turno> turnos;
	
}
