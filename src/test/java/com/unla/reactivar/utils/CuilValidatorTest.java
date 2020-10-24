package com.unla.reactivar.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CuilValidatorTest {
	
	@Test
	public void esCuilValidoTest() {
		assertEquals(true, CuilValidator.esCuilValido("20391080462", "hombre", ""));		
		assertEquals(true, CuilValidator.esCuilValido("23392766499", "hombre", "39276649"));
		assertEquals(true, CuilValidator.esCuilValido("30715002120", "", ""));
	}
	
	@Test
	public void esCuildValidoMenorMillonTest() {
		assertEquals(true, CuilValidator.esCuilValido("20085684680", "hombre", "8568468"));
	}

}
