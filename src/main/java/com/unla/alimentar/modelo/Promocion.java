package com.unla.alimentar.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "promocion")
public class Promocion {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idPromocion ;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean habilitada;
	private String descripcion;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonBackReference
	private Emprendimiento emprendimiento;
	

	@OneToMany(mappedBy = "promocion")
	@JsonManagedReference
	private List<Articulo> listArticulos;
	

}
