package it.unict.spring.application.controller.data.publicaccess;

import it.unict.spring.application.persistence.model.user.Organization;
import it.unict.spring.application.service.user.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

@RestController
@RequestMapping("/public/api")
public class PublicWebController
{       
    @Autowired
    private OrganizationService orgService;
    
      
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


