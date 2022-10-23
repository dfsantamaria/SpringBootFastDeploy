package it.unict.spring.platform.configuration.security.websecurity;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.configuration.security.authentication.CustomAccessDeniedHandler;
import it.unict.spring.platform.configuration.security.authentication.CustomAuthEntryPoint;
import it.unict.spring.platform.configuration.security.login.JdbcTokenRepositoryImpl;
import it.unict.spring.platform.configuration.security.logout.CustomLogoutSuccessHandler;
import it.unict.spring.platform.configuration.security.login.CustomLoginFailureHandler;
import it.unict.spring.platform.configuration.security.login.CustomLoginSuccessHandler;
import it.unict.spring.platform.configuration.security.matchers.MaintenanceRequestMatcher;
import it.unict.spring.platform.service.security.CustomUserDetailsService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig 
{
 @Autowired
 private DaoAuthenticationProvider authProvider;      
 @Autowired
 private DataSource dataSource;  
 @Autowired
 private CustomUserDetailsService customUserDetailsService;
 @Autowired 
 MaintenanceRequestMatcher maintenanceRequestMatcher;
 @Autowired
 private CustomAuthEntryPoint unauthorizedHandler; // handle unauthorized request,
 
 
 @Value("${rememberme.key}")
 private String rememberkey;
 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
                    
        http.csrf().disable().              
 
                authorizeHttpRequests().requestMatchers(maintenanceRequestMatcher).denyAll().                
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
                permitAll(). 
                
                and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).
                and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).         
                
                and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository()).userDetailsService(customUserDetailsService)
                .rememberMeCookieName("remember-me").rememberMeParameter("remember-me").
                key(rememberkey).
                tokenValiditySeconds(20 * 24 * 60 * 60).
                
                and()
                .logout()
                .logoutUrl("/auth/api/access/login/signout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")                
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
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository()
    {
        JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
        tokenRepo.setDataSource(dataSource);
        return tokenRepo;
    }
    
    @Bean
     public AccessDeniedHandler accessDeniedHandler()
     {
        return new CustomAccessDeniedHandler();
     }
}