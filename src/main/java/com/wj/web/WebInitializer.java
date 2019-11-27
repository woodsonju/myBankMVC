package com.wj.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.wj.MyBankApplication;

/*	Classe pour mon fichier war
 * 	Quand on demarre un war, c'est la classe qui va être 
 * 	demarrer en premier
 */
public class WebInitializer extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		return builder.sources(MyBankApplication.class);
	}
	
	

}
