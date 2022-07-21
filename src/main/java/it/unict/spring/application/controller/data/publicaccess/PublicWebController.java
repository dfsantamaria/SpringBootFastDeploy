package it.unict.spring.application.controller.data.publicaccess;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */
import it.unict.spring.application.persistence.model.user.Organization;
import it.unict.spring.application.service.user.OrganizationService;
import it.unict.spring.application.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public/api")
public class PublicWebController
{       
    @Autowired
    private OrganizationService orgService;
    @Autowired
    private UserService userService;
      
    @GetMapping("orgs")
    public String nonLoggedOrgMap()
    {        
       if(orgService.findByName("test").isEmpty())
          orgService.save(new Organization("test"));
       if(orgService.findAll().isEmpty())
           return "empty";
       return
            orgService.findAll().toString();
      
    }
}


