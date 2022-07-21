package it.unict.spring.platform.services.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.security.CustomUserDetailsService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import java.util.List;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.ANY, connection=EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes={CustomUserDetailsService.class})
@EntityScan(basePackages =  {"it.unict.spring.platform.persistence.model"})
//@EnableJpaRepositories(basePackages = {"it.unict.spring.platform.persistence.repository.*"})
@ComponentScan(basePackages = {"it.unict.spring.platform.service.*", 
                               "it.unict.spring.platform.configuration.*"})
@Transactional
public class CustomUserDetailsServiceTest
{
   @SpyBean
   CustomUserDetailsService  detailService;
   @SpyBean
   UserService userServ;
   
   private final String mail="daniele.santamaria@unict.it";
   private final String username = "dfsantamaria";
   
   @Test
   @Transactional
   public void loadUserByUsername() throws MultipleUsersFoundException 
   {
       List<UserAccount> users = userServ.findByUsername(username);
       if(users.isEmpty())
       {
           UserAccount user=userServ.getSuperAdminUser(username, "lll@@", mail, "Univeristy of Catania");
           userServ.setEnabled(user, true);
       }
       CustomUserDetails details = (CustomUserDetails) detailService.loadUserByUsername(username);
       assertEquals(details.getUsername(), username);
       for(GrantedAuthority gr : details.getAuthorities())
       {
           assertEquals(gr.getAuthority(), "ROLE_SUPERADMIN");     
       }
       assertEquals(details.getMail(), mail);
   }
}
