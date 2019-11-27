package com.wj.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class BankSecurityConfig extends WebSecurityConfigurerAdapter{
	
	/*
	 * On demande à Spring d'utiliser le même datasource que celle definie 
	 * dans le application.properties
	 */
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * Avec l'objet AuthenticationManagerBuilder : 
		 * on va specifier quels sont les utilisateurs qui ont le droit d'acceder
		 * à l'application
		 */
		
		/*
		 * On utilise inMemoryAuthentication d'abord, ensuite on utilisera 
		 * jdbcAuthentication. 
		 */
		
		/* inMemoryAuthentication:
		 * withUser() : On spécifie les utilisateurs avec withUser
		 * password(): On spécifie le mot de passe
		 * roles() : Quels sont les rôles à cette utilisateur
		 */
		//auth.inMemoryAuthentication().withUser("admin").password("1234").roles("USER", "ADMIN");
		//auth.inMemoryAuthentication().withUser("user").password("1234").roles("USER");
		
		
		
		/*
		 * jdbcAuthentication() : Les utilisateurs seront mémorisées dans une base de données
		 * 	datasource : On demande à Spring d'utiliser le même datasource que celle 
		 *  definie dans le application.properties
		 *  Pour que Spring security sache que login répresente le Username il faut utiliser 
		 *  le mot principal,  et credentials pour le password
		 *  La methode authoritiesByUsernameQuery permet de savoir quels sont les rôles 
		 *  de l'utilisateur.
		 *  la méthode passwordEncoder() permet de coder le password en MD5
		 *  Spécifier le préfix du rôle qui vat être utiliser par spring securité;
		 *  quand il va charger un rôle il ajoutera le prefixe spécifier
		 */
		auth.jdbcAuthentication().dataSource(dataSource)
		//Spring va chercher l'utilisateur et le role de l'utilisateur
		.usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
		.authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
		.passwordEncoder(new Md5PasswordEncoder())
		.rolePrefix("ROLE_");// On dit à Spring Security le prefix qu'il va utiliser
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * 	L'objet HttpSecurity definie les règles de securité
		 * 	formLogin(): L'operation d'authentification passe par un formulaire
		 * d'authentification.
		 * 
		 * 
		 * formLogin().loginPage("/login") : permet de personaliser le formulaire
		 * l'action /login me retournera un page HTML avec un formulaire personalisé
		 * En appelant seulement la méthode formLogin(), Spring security crée
		 * un formulaire préConfiguré par défaut.
		 * */
		http.formLogin().loginPage("/login");
		
		/*
		 * On défini un rôle pour chaque action, url(/operations, 
		 * /consulterCompte, /saveOperation) afin d'y accéder.
		 * On securise les ressources de l'application
		 */
		http.authorizeRequests().antMatchers("/operations", "/consulterCompte").hasRole("USER");
		http.authorizeRequests().antMatchers("/saveOperation").hasRole("ADMIN");
		
		/*
		 * Permet de Personaliser une page
		 * Exemple d'une page qui affiche :
		 * "There was an unexpected error (type=Forbidden, status=403).
		 * Access is denied"
		 * Ici, s'il n'a pas le droit d'acceder, il va vers l'action /403 
		 * */
		http.exceptionHandling().accessDeniedPage("/403");
	}


	
	
}
