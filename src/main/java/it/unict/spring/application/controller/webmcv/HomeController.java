/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.controller.webmcv;
 
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 

@Controller
public class HomeController
{
 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model)
        {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale); 
		String formattedDate = dateFormat.format(date); 
		model.addAttribute("serverTime", formattedDate ); 
		return "public/home";
	}
}