package com.unla.reactivar.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "estadoEmprendimiento")
public class EstadoEmprendimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEstadoEmprendimiento;

	@Column(unique = true)
	private String estado;

}
