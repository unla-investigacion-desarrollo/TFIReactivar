package com.unla.alimentar.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "tipoEmprendimiento")
public class TipoEmprendimiento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTipoEmprendimiento;
	private String nombre;
	
	@OneToMany(mappedBy = "tipoEmprendimiento")
	@JsonManagedReference
	private List<Emprendimiento> emprendimientos;
}
