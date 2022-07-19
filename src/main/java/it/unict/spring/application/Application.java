package it.unict.spring.application;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.exception.user.MultipleUsersFoundException;
import it.unict.spring.application.persistence.model.user.SecureToken;
import it.unict.spring.application.persistence.model.user.UserAccount;
import it.unict.spring.application.persistence.model.user.UserRegister;
import it.unict.spring.application.service.user.SecureTokenService;
import it.unict.spring.application.service.user.UserRegisterService;
import it.unict.spring.application.service.user.UserService;
import java.sql.SQLException;
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

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

@SpringBootApplication
public class Application extends SpringBootServletInitializer
{
  private static final Logger applogger = LoggerFactory.getLogger(Application.class);  
  @Autowired
  private ApplicationContext appContext;
  
  @Autowired  
  @Qualifier("dataEntityManager")  
  public LocalContainerEntityManagerFactoryBean dataEntityManagerFactory;
    
  @Autowired
  @Qualifier("userEntityManager")
  public LocalContainerEntityManagerFactoryBean userEntityManagerFactory;
 
  @Autowired
  public UserService userService;
  @Autowired
  public UserRegisterService registerService;
    
  @Autowired
  public SecureTokenService tokenService;
  
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
       applogger.info("Connected to DB schema: "+ ((DataSource)appContext.getBean("userSource")).getConnection().getCatalog());
       applogger.info("Connected to DB schema: "+ ((DataSource)appContext.getBean("dataSource")).getConnection().getCatalog());   
           }
      catch (SQLException ex)
      {
        applogger.info("Error on DBConnection: " + ex.toString());
      } 
     // catch (MultipleUsersFoundException ex)
     // {
    //      applogger.warn(ex.toString());
    //  }
      /* Data p = new Data("admin");
       Data p1 = new Data("admin2");
       EntityManager datamanager=dataEntityManagerFactory.getObject().createEntityManager();
       datamanager.getTransaction().begin();
       datamanager.persist(p); 
       datamanager.persist(p1);
       datamanager.getTransaction().commit(); 
       EntityManager usermanager=userEntityManagerFactory.getObject().createEntityManager();
       */    
      }    

      
      @EventListener(ContextRefreshedEvent.class)
      @Transactional
       public void afterStartupListener()
       {
        try
        {
           if(userService.findByMail("daniele.santamaria@unict.it").isEmpty())
           {
              UserAccount user = userService.getOrSetSuperAdminUser("dfsantamaria", "lll@@", "daniele.santamaria@unict.it", "Univeristy of Catania");
              userService.setEnabled(user, true); //enable user              
              UserRegister register=new UserRegister("Daniele", "Francesco", "Santamaria");
              userService.setRegister(register, user);         
           }
           
        } 
        catch (MultipleUsersFoundException ex) 
        {
          applogger.error( ex.toString());          
        }   
       }  
     
      
}

