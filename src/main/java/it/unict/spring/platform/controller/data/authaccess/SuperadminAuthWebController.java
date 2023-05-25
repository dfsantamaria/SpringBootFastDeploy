package it.unict.spring.platform.controller.data.authaccess;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth/api/superadmin")
public class SuperadminAuthWebController
{        
    @GetMapping(value="hello")
    public ResponseEntity<String> sayHello(@RequestParam(value = "myName", defaultValue = "World") String name)
    {
        return new ResponseEntity<>(String.format("Hello %s!", name), HttpStatus.OK);
    }
}