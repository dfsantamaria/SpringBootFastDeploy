package it.unict.spring.platform.controller.info.publicaccess;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.utility.user.PomReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public/api/info")
public class InfoController
{           
    private final PomReader pomreader=new PomReader();
       
    @GetMapping("about")
    public String nonLoggedOrgMap()
    {           
     return pomreader.getForkInfo().toString();
    }
}


