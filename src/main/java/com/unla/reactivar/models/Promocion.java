package com.unla.reactivar.models;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "promocion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoPromocion", 
discriminatorType = DiscriminatorType.STRING)
public class Promocion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idPromocion ;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean habilitada;
	private String descripcion;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonManagedReference
	private Emprendimiento emprendimiento;
	

	@OneToMany(mappedBy = "promocion")
	@JsonBackReference
	private List<Articulo> listArticulos;
	

}
