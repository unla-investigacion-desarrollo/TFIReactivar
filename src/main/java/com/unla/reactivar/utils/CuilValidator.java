package com.unla.reactivar.utils;

import org.apache.commons.lang3.StringUtils;

import com.unla.reactivar.exceptions.InvalidCuilCuit;


public class CuilValidator {

	private static final String HOMBRE = "hombre";
	private static final String MUJER = "mujer";
	private static final int MILLON_DIGITOS = 8;
	
	/*
	 * 27 es para mujeres 20 es para hombres 23 puede ser ambos (se usa cuando hay
	 * otro número igual) 30 empresas
	 */
	public static boolean esCuilValido(String cuil, String sexo, String dni) {
		boolean flag = true;
		
		if(!StringUtils.isBlank(dni) && dni.length() < MILLON_DIGITOS) {
			while(dni.length() < MILLON_DIGITOS) {
				dni = new StringBuilder("0").append(dni).toString();
			}
		}
		
		if(!StringUtils.isBlank(dni) && !cuil.substring(2, cuil.length()-1).equals(dni)) {
			throw new InvalidCuilCuit();
		}
		
		
		if(cuil.length() < 10) {
			throw new InvalidCuilCuit();
		}

		int[] arrayValidator = { 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 };
		char[] charCuilArray = cuil.toCharArray();
		int suma = 0;
		for (int i = 0; i < arrayValidator.length; i++) {
			suma += arrayValidator[i] * Integer.parseInt(String.valueOf(charCuilArray[i]));
		}

		int divisor = 11;
		int resto = suma % divisor;
		int validador = 0;

		if (resto == 1 && HOMBRE.equalsIgnoreCase(sexo)) {
			validador = 9;
		} else if (resto == 1 && MUJER.equalsIgnoreCase(sexo)) {
			validador = 4;
		} else if (resto != 0) {
			validador = divisor - resto;
		}

		char validadorChar = (char) (validador + '0');

		if (charCuilArray[charCuilArray.length - 1] != validadorChar) {
			throw new InvalidCuilCuit();
		}

		return flag;
	}

}
