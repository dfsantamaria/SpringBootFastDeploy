package it.unict.spring.platform.controller.publicaccess.home;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home
{                 
    @GetMapping("/")
    public @ResponseBody String home()
    {           
     JSONObject obj=new JSONObject();
     obj.put("status", "success");
     return obj.toString();
    }
}