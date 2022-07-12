package it.unict.spring.application.configuration.security;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig 
{
 @Autowired
 DaoAuthenticationProvider authProvider;
      
   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
                    
        http.csrf().disable().
                authorizeHttpRequests().antMatchers( "/public/**", "/").permitAll().
                antMatchers("/auth/superadmin/**").hasAnyRole("SUPERADMIN").
                antMatchers("/auth/admin/**").hasAnyRole("SUPERADMIN","ADMIN").
                antMatchers("/auth/staff/**").hasAnyRole("SUPERADMIN","ADMIN","STAFF").
                antMatchers("/auth/standarduser/**").hasAnyRole("SUPERADMIN","ADMIN","STAFF","STANDARDUSER").
                
                anyRequest().authenticated().and().httpBasic();
                        
        http.
                formLogin().                
                permitAll().
                successHandler(new CustomLoginSuccessHandler()).
                
                and()
                .logout()
                .permitAll();       
        return http.build();     
    }

        
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        //auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder);
        auth.authenticationProvider(authProvider);
    }
    
      
    @Bean
    public RoleHierarchy roleHierarchy()
    {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = "superadmin > admin > staff > standarduser";
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
    }        
    
        
}