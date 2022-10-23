package it.unict.spring.platform.configuration.security.authentication;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = 565662170056829238L;
    // invoked when user tries to access a secured REST resource without supplying any credentials,
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException
    {       
        response.sendRedirect(request.getContextPath()+"/public/api/access/error/forbidden");
    }
}