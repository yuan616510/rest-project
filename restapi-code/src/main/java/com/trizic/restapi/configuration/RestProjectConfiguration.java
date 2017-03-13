package com.trizic.restapi.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration for Spring MVC
 * @author Yuan
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.trizic.restapi")
public class RestProjectConfiguration {

}
