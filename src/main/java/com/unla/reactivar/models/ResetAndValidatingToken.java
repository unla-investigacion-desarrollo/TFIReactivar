package com.unla.reactivar.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.unla.reactivar.utils.DateUtils;

import lombok.Data;

@Entity
@Data
public class ResetAndValidatingToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	@OneToOne(fetch = FetchType.EAGER, targetEntity = Persona.class)
	@JoinColumn(nullable = false, name = "idPersona")
	private Persona persona;

	private Date expiryDate;
	
	private int expiration;

	public ResetAndValidatingToken() {

	}
	
	public ResetAndValidatingToken(String token, Persona persona, int expiration) {
		this.token = token;
		this.persona = persona;
		this.expiration = expiration;
        this.expiryDate = calculateExpiryDate(expiration);
	}

	public ResetAndValidatingToken(String token, int expiration) {
		this.token = token;
        this.expiryDate = calculateExpiryDate(expiration);
	}
	
    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        return new Date(DateUtils.fechaHoy().getTime() + expiryTimeInMinutes);
    }

    public void updateToken(final String token, int expiration) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(expiration);
    }
}