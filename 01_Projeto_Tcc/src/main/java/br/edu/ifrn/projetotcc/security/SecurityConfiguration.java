package br.edu.ifrn.projetotcc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.projetotcc.service.UsuarioService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService service;
	
    protected void configure(HttpSecurity http) throws Exception{
    	
      //http.authorizeRequests().antMatchers("/").permitAll();
        
        http.authorizeRequests()
		.antMatchers("/css/**,/imagens/**,/js/**").permitAll()
		.antMatchers("/publico/**").permitAll()
		
		.anyRequest().authenticated() 
		.and() 
			.formLogin()
			.loginPage("/login") 
			.defaultSuccessUrl("/",true) 
			.failureUrl("/login-error") 
			.permitAll()
		.and()
			.logout()
			.logoutUrl("/logout") 
			.logoutSuccessUrl("/")
		.and()
			.rememberMe();
    }
    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
