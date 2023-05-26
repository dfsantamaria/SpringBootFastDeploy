package it.unict.spring.platform.controller.publicaccess.error;

import org.json.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Error implements ErrorController {
	

	@RequestMapping(value = "/error")
	public String myerror()
        {
          JSONObject obj=new JSONObject();
          obj.put("status","error");
	  return obj.toString();
	}
}