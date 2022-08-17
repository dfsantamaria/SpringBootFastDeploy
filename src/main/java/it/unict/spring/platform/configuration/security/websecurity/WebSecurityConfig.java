package it.unict.spring.platform.configuration.security.websecurity;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.configuration.security.logout.CustomLogoutSuccessHandler;
import it.unict.spring.platform.configuration.security.login.CustomLoginFailureHandler;
import it.unict.spring.platform.configuration.security.login.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


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
 
                authorizeHttpRequests().                
                antMatchers("/public/**", "/").permitAll(). 
                antMatchers("/auth/api/all/**").authenticated().
                antMatchers("/auth/api/superadmin/**").hasAnyRole("SUPERADMIN").
                antMatchers("/auth/api/admin/**").hasAnyRole("SUPERADMIN","ADMIN").
                antMatchers("/auth/api/staff/**").hasAnyRole("SUPERADMIN","ADMIN","STAFF").
                antMatchers("/auth/api/standarduser/**").hasAnyRole("SUPERADMIN","ADMIN","STAFF","STANDARDUSER").
                
                anyRequest().authenticated().and().httpBasic();
                        
        http.
                formLogin().
                loginPage("/public/api/access/login/signin").  
                loginProcessingUrl("/public/api/access/login/signin").
                successHandler(authenticationSuccessHandler()).
                failureHandler(authenticationFailureHandler()).
                defaultSuccessUrl("/auth/api/all/accountView").
                permitAll().   
                
                and()
                .logout()
                .logoutUrl("/auth/api/access/login/signout")
                .logoutSuccessHandler(logoutSuccessHandler())                
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
    
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return  new CustomLoginFailureHandler();
    }    
    
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return  new CustomLoginSuccessHandler();
    } 
    
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler()
    {
        return new CustomLogoutSuccessHandler();
    }
}