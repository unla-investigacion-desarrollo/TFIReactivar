package com.unla.reactivar.vo;

import lombok.Data;

@Data
public class PasswordRecoveryVo {

	private String token;
	private String newPassword;
	
}
