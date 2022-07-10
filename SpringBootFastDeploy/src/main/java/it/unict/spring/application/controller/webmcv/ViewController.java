/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.controller.webmcv;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/").setViewName("redirect:/public/landing/main");
        registry.addViewController("/").setViewName("forward:/public/landing/main/index.html");
       // registry.addViewController("/public/account/login").setViewName("redirect:/public/account");
       // registry.addViewController("/login").setViewName("login");
       // registry.addViewController("/login.html");
    }
}