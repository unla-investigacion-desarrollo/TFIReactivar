package com.unla.reactivar.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "promocion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoPromocion", discriminatorType = DiscriminatorType.STRING)
public abstract class Promocion implements Serializable {

	private static final long serialVersionUID = 3618308926536344259L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPromocion;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaInicio;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaFin;
	private boolean habilitada;
	@Column(unique = true)
	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonManagedReference
	private Emprendimiento emprendimiento;

	@OneToMany(mappedBy = "promocion")
	@JsonBackReference
	private List<Articulo> listArticulos;

}
