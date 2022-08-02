package it.unict.it.spring.platform.controller.data.publicaccess;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.Application;
import it.unict.spring.platform.utility.user.PomReader;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class InfoControllerTest
{       
    @Autowired
    private MockMvc mvc;
    private String name="name";
    
      
    @Test
    public void checkPomCopied() throws FileNotFoundException
    {
     PomReader pom= new PomReader();
     assertNotNull(pom.getForkInfo().toString());
    }
    
    @Test
    @WithMockUser(username = "admin", roles = { "STAFF" })
    public void aboutAuthTest() throws Exception
    {        
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/public/api/info/about"));
        perform.andExpect(status().isOk());
        assertNotNull(perform.andReturn().getResponse().getContentAsString());
        
    }
    
    @Test
    @WithAnonymousUser
    void aboutUnauthorizedTest() throws Exception 
    {
       ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/public/api/info/about"));
       perform.andExpect(status().isOk());
       assertNotNull(perform.andReturn().getResponse().getContentAsString());
    }
}
