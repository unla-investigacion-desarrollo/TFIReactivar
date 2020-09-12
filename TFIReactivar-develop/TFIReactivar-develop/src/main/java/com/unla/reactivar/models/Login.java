package com.unla.reactivar.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "login")
public class Login {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idLogin;
	@Column(unique = true)
	private String email;
	@JsonInclude(Include.NON_NULL)
	private String clave;
	@Transient
	private String token;

}
