package com.unla.reactivar.utils;

import java.util.HashMap;
import java.util.Map;

public enum EnumDiaSemana {
	DOMINGO("domingo", 1),
	LUNES("lunes", 2),
	MARTES("martes", 3),
	MIERCOLES("miercoles", 4),
	JUEVES("jueves", 5),
	VIERNES("viernes", 6),
	SABADO("sabado", 7),
	NONE("", 0);
	
	private String dia;
	private int nroDia;

	private static Map<String, EnumDiaSemana> mapSemanaDiaKey = new HashMap<>();
	private static Map<Integer, EnumDiaSemana> mapSemanaNroDiaKey = new HashMap<>();

	static {
		for (EnumDiaSemana semana : EnumDiaSemana.values()) {
			mapSemanaDiaKey.put(semana.dia, semana);
			mapSemanaNroDiaKey.put(semana.nroDia, semana);
		}
	}

	public static EnumDiaSemana getByDia(String dia) {
		EnumDiaSemana tipoTransaccionEnum = mapSemanaDiaKey.get(dia);
		if (tipoTransaccionEnum != null)
			return tipoTransaccionEnum;
		return NONE;
	}

	public static EnumDiaSemana getByNroDia(int nroDia) {
		EnumDiaSemana tipoTransaccionEnum = mapSemanaNroDiaKey.get(nroDia);
		if (tipoTransaccionEnum != null)
			return tipoTransaccionEnum;
		return NONE;
	}

	private EnumDiaSemana(String dia, int nroDia) {
		this.dia = dia;
		this.nroDia = nroDia;
	}

	public String getDiaKey() {
		return this.dia;
	}

	public int getNroDiaKey() {
		return this.nroDia;
	}

}
