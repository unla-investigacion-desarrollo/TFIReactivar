package com.unla.reactivar.models;

import java.util.List;

import javax.persistence.Column;
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
@Table(name = "tipoEmprendimiento")
public class TipoEmprendimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTipoEmprendimiento;
	@Column(unique = true)
	private String nombre;

	@OneToMany(mappedBy = "tipoEmprendimiento")
	@JsonBackReference
	private List<Emprendimiento> emprendimientos;
}
