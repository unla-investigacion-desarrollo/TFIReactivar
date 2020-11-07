package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class ValConfLocalVo {
	
	private String diaSemana;
	private String turno1Desde;
	private String turno1Hasta;
	private String turno2Desde;
	private String turno2Hasta;
	private int intervaloTurnos;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValConfLocalVo other = (ValConfLocalVo) obj;
		if (diaSemana == null) {
			if (other.diaSemana != null)
				return false;
		} else if (!diaSemana.equals(other.diaSemana))
			return false;
		if (intervaloTurnos != other.intervaloTurnos)
			return false;
		if (turno1Desde == null) {
			if (other.turno1Desde != null)
				return false;
		} else if (!turno1Desde.equals(other.turno1Desde))
			return false;
		if (turno1Hasta == null) {
			if (other.turno1Hasta != null)
				return false;
		} else if (!turno1Hasta.equals(other.turno1Hasta))
			return false;
		if (turno2Desde == null) {
			if (other.turno2Desde != null)
				return false;
		} else if (!turno2Desde.equals(other.turno2Desde))
			return false;
		if (turno2Hasta == null) {
			if (other.turno2Hasta != null)
				return false;
		} else if (!turno2Hasta.equals(other.turno2Hasta))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diaSemana == null) ? 0 : diaSemana.hashCode());
		result = prime * result + intervaloTurnos;
		result = prime * result + ((turno1Desde == null) ? 0 : turno1Desde.hashCode());
		result = prime * result + ((turno1Hasta == null) ? 0 : turno1Hasta.hashCode());
		result = prime * result + ((turno2Desde == null) ? 0 : turno2Desde.hashCode());
		result = prime * result + ((turno2Hasta == null) ? 0 : turno2Hasta.hashCode());
		return result;
	}
	
	

}
