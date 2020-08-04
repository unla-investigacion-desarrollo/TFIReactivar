package com.unla.alimentar.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "tipoEmprendimiento")
public class TipoEmprendimiento {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idTipoEmprendimiento;
	private String nombre;
	
	@OneToMany(mappedBy = "tipoEmprendimiento")
	@JsonManagedReference
	private List<Emprendimiento> emprendimientos;
}
