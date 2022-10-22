package it.unict.spring.platform.configuration.security.matchers;

import it.unict.spring.platform.utility.user.SettingStore;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceRequestMatcher implements RequestMatcher
{
    @Autowired 
    SettingStore settingStore;

    @Override
    public boolean matches(HttpServletRequest request)
    {
        return settingStore.getMaintenanceMode() && !request.isUserInRole("ROLE_SUPERADMIN");
    }
}