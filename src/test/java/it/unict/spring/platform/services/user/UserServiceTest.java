package it.unict.spring.platform.services.user;

import it.unict.spring.platform.Application;
import it.unict.spring.platform.dto.user.PageDTO;
import it.unict.spring.platform.dto.user.UserSearchDTO;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

//remember to modify the file schema.sql if the name of "data" schema changes

import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.service.user.SearchManagerService;
import it.unict.spring.platform.service.user.UserRegisterService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static junit.framework.TestCase.assertFalse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest
{

    //@Autowired
   // private TestEntityManager entityManager;
    @SpyBean
    private  UserService userServ;  
    @SpyBean 
    private UserRegisterService regService;
    private final String mail="test@unict.it";
    private final String username = "testatunict";
    private Optional<UserAccount> users;    
    private final String name = "myName";
    private final String middleName = "middleName";
    private final String lastName = "lastName";
    @SpyBean
    SearchManagerService searchManager;
   
    @BeforeAll    
    public void createUser() throws MultipleUsersFoundException
    {
       users = userServ.findByUsername(username);
       if(users.isEmpty())
       {
          UserAccount user = userServ.getSuperAdminUser(username, "lll@@", mail,
                                                         UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(),
                                                         "My nice organization");
          userServ.setEnabled(user, true);  
          userServ.save(user);
          
          UserRegister register=new UserRegister(name, middleName, lastName);
          userServ.addRegisterToUser(register, user);
          regService.save(register);
       }
                  
       users = userServ.findByUsername(username);
       assertTrue(users.get().isEnabled());
       assertEquals(users.get().getMail(), mail);
       
       Optional<UserRegister> register = regService.findByUser(users.get());
       assertEquals(register.get().getLastname(), lastName);
              
    }
    
    @AfterAll
    public void clear()
    {
       Optional<UserAccount> findusers = userServ.findByUsername(username);
       userServ.delete(findusers.get());
    }
    
    @Test
    public void testFindByName()
    {        
        Optional<UserAccount> findusers = userServ.findByUsername(username);
        assertFalse(findusers.isEmpty());
        assertEquals(findusers.get().getUsername(), username); 
        
    }

    @Test
    public void testPageFindByMailOrUsername()
    {        
     Page<UserAccount> pages = userServ.findByMailOrUsername(mail, PageRequest.of(0, 10));    
     assertTrue(pages.getTotalElements()> 0);    
     assertTrue(pages.getTotalPages()> 0);
    }
    
    @Test
    public void testPageFindByFirstnameOrMiddleNameOrLastname()
    {        
     Page<UserRegister> pages = regService.findByFirstnameOrMiddlenameOrLastname(name, middleName, lastName, PageRequest.of(0, 10));    
     assertTrue(pages.getTotalElements()> 0);    
     assertTrue(pages.getTotalPages()> 0);
     UserRegister reg = pages.getContent().get(0);
     assertEquals(reg.getLastname(), lastName);
    }
    
    @Test 
    public void testSearchManager()
    {
       UserSearchDTO testUser=new UserSearchDTO();
       testUser.setMail(mail);
       testUser.setFirstName("");
       testUser.setMiddleName("");
       testUser.setLastName("");
       testUser.setOrgname("");
       
       PageDTO pageSearch=new PageDTO();
       pageSearch.setCurrentPage(1);
       pageSearch.setPageSpan(10);
       pageSearch.setItemsNumber("10");
       Page<UserAccount> pages = searchManager.search(userServ, pageSearch, testUser);
       List<UserSearchDTO> results = userServ.createUserSearchDTOFromPage(pages);
       assertFalse(results.isEmpty());
    }
}

