package it.unict.it.spring.application.controller.data.authaccess;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */


import it.unict.spring.application.Application;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class SuperAdminWebControllerTest
{       
    @Autowired
    private MockMvc mvc;
    private String name="name";
    
    @Test
    @WithMockUser(username = "admin", roles = { "SUPERADMIN" })
    public void sayHelloAuthTest() throws Exception
    {        
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/auth/api/superadmin/hello").param("myName", name));
        perform.andExpect(status().isOk());
        assertEquals(perform.andReturn().getResponse().getContentAsString(),"Hello "+name+"!");
    }
    
    @Test
    @WithMockUser(username = "admin", roles = { "STAFF" })
    public void sayHelloNonAuthTest() throws Exception
    {        
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/auth/api/superadmin/hello").param("myName", name));
        perform.andExpect(status().isForbidden());
        
    }
    
    @Test
    @WithAnonymousUser
    void sayHelloUnauthorizedTest() throws Exception 
    {
       ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/auth/api/superadmin/hello").param("myName", name));
       perform.andExpect(status().isUnauthorized());
    }
}
