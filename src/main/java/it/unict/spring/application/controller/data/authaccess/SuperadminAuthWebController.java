package it.unict.spring.application.controller.data.authaccess;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author danie
 */

@RestController
@RequestMapping("/auth/api/superadmin")
public class SuperadminAuthWebController
{
        
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name)
    {
        System.out.print("hello");
        return String.format("Hello %s!", name);
    }
}
