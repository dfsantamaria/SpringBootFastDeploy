package it.unict.it.spring.platform.controller.data.publicaccess;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.Application;
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
public class PublicWebControllerTest
{       
    @Autowired
    private MockMvc mvc;
    private String name="name";
    
       
    @Test
    @WithMockUser(username = "admin", roles = { "STAFF" })
    public void orgsAuthTest() throws Exception
    {        
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/public/api/orgs"));
        perform.andExpect(status().isOk());
        assertNotNull(perform.andReturn().getResponse().getContentAsString());
        
    }
    
    @Test
    @WithAnonymousUser
    void orgsUnauthorizedTest() throws Exception 
    {
       ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/public/api/orgs"));
       perform.andExpect(status().isOk());
       assertNotNull(perform.andReturn().getResponse().getContentAsString());
    }
}
