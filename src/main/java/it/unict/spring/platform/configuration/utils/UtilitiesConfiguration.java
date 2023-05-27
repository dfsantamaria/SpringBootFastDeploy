package it.unict.spring.platform.configuration.utils;

import it.unict.spring.platform.utility.user.ServerUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilitiesConfiguration
{   
    @Bean
    public ServerUtils serverUtils(@Value("${server.servlet.context-path}") String context)
    {
      ServerUtils utils=new ServerUtils(context);
      return utils;
    } 
}
