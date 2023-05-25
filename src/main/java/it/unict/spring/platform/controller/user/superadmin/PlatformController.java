package it.unict.spring.platform.controller.user.superadmin;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.service.platform.PlatformStatusService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth/api/superadmin/platform")
public class PlatformController
{    
    @Autowired 
    PlatformStatusService platformService;    
        
    @GetMapping("toggleMaintenance")
    public ResponseEntity<String> toggleMaintenanceMode()
     {
        JSONObject obj=new JSONObject();
        obj.put("status", platformService.toggleMaintenanceMode());
        return new ResponseEntity<>(obj.toString(), HttpStatus.OK);       
     }
}
