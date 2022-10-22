package it.unict.spring.platform;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.persistence.model.platform.PlatformStatus;
import it.unict.spring.platform.persistence.model.user.SecureToken;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.service.platform.PlatformStatusService;
import it.unict.spring.platform.service.user.PrivilegeService;
import it.unict.spring.platform.service.user.SecureTokenService;
import it.unict.spring.platform.service.user.UserRegisterService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class Application extends SpringBootServletInitializer
{
  private static final Logger applogger = LoggerFactory.getLogger(Application.class);  
  @Autowired
  private ApplicationContext appContext;
  
  @Autowired  
  @Qualifier("mainDataEntityManager")  
  public LocalContainerEntityManagerFactoryBean dataEntityManagerFactory;
    
 
  @Autowired
  public UserService userService;
  @Autowired
  public UserRegisterService registerService;    
  @Autowired
  public SecureTokenService tokenService;
  @Autowired
  public PrivilegeService privService;
  @Autowired
  public PlatformStatusService platformService;
  
  
  @Override
  public SpringApplicationBuilder configure(SpringApplicationBuilder application)
        {
		return application.sources(Application.class);
                
	}
  
  public static void main(String[] args) 
   {   
    SpringApplication.run(Application.class, args);      
   }
  
  @PostConstruct   
  public void postConstruct()
   {              
      applogger.info("Post-construct message: Application started");
      System.out.println("Console -- Application started"); 
      try
      {   
       
       applogger.info("Connected to DB schema: "+ ((DataSource)appContext.getBean("mainDataSource")).getConnection().getCatalog());   
      }
      catch (SQLException ex)
      {
        applogger.info("Error on DBConnection: " + ex.toString());
      }      
      }    

      
      @EventListener(ContextRefreshedEvent.class)
      @Transactional
       public void afterStartupListener()
       {
        try
        {  
           if(!platformService.isStatusDefined()) 
               platformService.save(new PlatformStatus(false));
           privService.startUpPrivileges();             
           if(userService.findByMail("daniele.santamaria@unict.it").isEmpty())
           {
              UserAccount user = userService.getSuperAdminUser("dfsantamaria", "lll@@", "daniele.santamaria@unict.it",
                                                                 UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(),
                                                                 "University of Catania"
                                                                 );
              userService.setEnabled(user, true); //enable user              
              UserRegister register=new UserRegister("Daniele", "Francesco", "Santamaria");
              userService.addRegisterToUser(register,user);
              registerService.save(register);
              
              userService.createLoginInfo(user);            
              
              SecureToken token = tokenService.generateToken(user, "FReg");
              token.setIsConsumed(Timestamp.valueOf(LocalDateTime.now()));
              tokenService.save(token);
              userService.save(user);
           }
           
        } 
        catch (MultipleUsersFoundException ex) 
        {
          applogger.error( ex.toString());            
        }   
       }  
     
      
}

