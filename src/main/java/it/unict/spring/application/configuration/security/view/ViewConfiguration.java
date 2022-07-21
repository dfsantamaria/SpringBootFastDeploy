package it.unict.spring.application.configuration.security.view;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {        
      //registry.addViewController("/public/access/signin").setViewName("redirect:/public/access/signin");
     // registry.addViewController("/public/access/signin").setViewName("forward:/public/access/signin");     
    }
}