package com.unla.reactivar.services;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.unla.reactivar.exceptions.EmailSenderException;
import com.unla.reactivar.models.Persona;

@Service
public class MailSenderService {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private JavaMailSenderImpl sender;

	@Value("${email.verify.templates.directory}")
	private String verifyUserEmail;

	@Value("${email.recovery.templates.directory}")
	private String recoveryPasswordEmail;

	@Value("${email.images.directory}")
	private String imagesDirectory;

	@Value("${email.host}")
	private String host;

	@Value("${email.port}")
	private int port;

	@Value("${email.protocol}")
	private String protocol;

	@Value("${email.username}")
	private String username;

	@Value("${email.password}")
	private String password;

	@Value("${email.smtp.auth}")
	private String smtpAuth;

	@Value("${email.smtp.starttls.eneable}")
	private String smtpStartTls;

	@Value("${email.smtp.quitwait}")
	private String smtpQuitWait;

	@Value("${email.smtp.ssl.trust}")
	private String smtpSslTrust;

	@PostConstruct
	private void postConstruct() {
		sender = new JavaMailSenderImpl();
		sender.setHost(host);
		sender.setPort(port);
		sender.setProtocol(protocol);
		sender.setUsername(username);
		sender.setPassword(password);
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", smtpAuth);
		javaMailProperties.put("mail.smtp.starttls.enable", smtpStartTls);
		javaMailProperties.put("mail.smtp.quitwait", smtpQuitWait);
		javaMailProperties.put("mail.smtp.ssl.trust", smtpSslTrust);
		sender.setJavaMailProperties(javaMailProperties);
	}

	public void constructResetTokenEmail(String token, Persona persona) {
		File file = new File(recoveryPasswordEmail);
		String message = "";
		try {
			message = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		constructEmail("ReactivAR - Restablecer Contraseña", message, persona.getLogin().getEmail(), token);
	}

	public void constructValidateEmail(String token, Persona persona) {
		File file = new File(verifyUserEmail);
		String message = "";
		try {
			message = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		constructEmail("ReactivAR - Verificar Email", message, persona.getLogin().getEmail(), token);
	}

	public void constructEmail(String subject, String body, String emailReceiver, String token) {
		MimeMessage message = sender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(subject);
			helper.setTo(emailReceiver);
			helper.setText(body.replace("cid:token", token), true);
			helper.addInline("emailLogo", new File(imagesDirectory + "email.png"));
			helper.setFrom(new InternetAddress(username));
		} catch (MessagingException e) {
			logger.error(e.getLocalizedMessage());
			throw new EmailSenderException();
		}

		sender.send(message);
	}

}
