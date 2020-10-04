package com.unla.reactivar.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = { "com.unla.reactivar", "com.unla.reactivar.config", "com.unla.reactivar.controllers",
		"com.unla.reactivar.exceptions", "com.unla.reactivar.exceptions.handler",
		"com.unla.reactivar.exceptions.models", "com.unla.reactivar.models", "com.unla.reactivar.repositories",
		"com.unla.reactivar.security", "com.unla.reactivar.services", "com.unla.reactivar.task", "com.unla.reactivar.firebase","com.unla.reactivar.utils"})
@Configuration
@PropertySource("file:${root.path}/app-directory/cfg/security/config.properties")
@PropertySource("file:${root.path}/app-directory/cfg/mail/config.properties")
@PropertySource("file:${root.path}/app-directory/cfg/notifications/config.properties")
public class RootConfig {

}
