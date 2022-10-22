package it.unict.spring.platform.configuration.security.matchers;

import it.unict.spring.platform.service.platform.PlatformStatusService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceRequestMatcher implements RequestMatcher
{
    @Autowired 
    PlatformStatusService platformService;

    @Override
    public boolean matches(HttpServletRequest request)
    {        
        return platformService.isMaintenanceMode()
        && !request.isUserInRole("ROLE_SUPERADMIN")
        && !request.getServletPath().startsWith("/public");
        
    }
}