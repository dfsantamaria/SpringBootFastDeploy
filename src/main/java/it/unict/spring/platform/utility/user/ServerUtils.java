package it.unict.spring.platform.utility.user;

import javax.servlet.http.HttpServletRequest;

public class ServerUtils
{
    private final String context;
    public ServerUtils(String context)
    {
     this.context=context;
    }            
    
    public  String getContextURL(HttpServletRequest request)
    {    
      return request.getRequestURL().substring(0,request.getRequestURL().indexOf(request.getRequestURI()))+context;          
    }
}
