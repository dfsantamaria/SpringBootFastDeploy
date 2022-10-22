package it.unict.spring.platform.utility.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */
import it.unict.spring.platform.persistence.model.user.Privilege;
import org.springframework.ui.Model;
import java.util.Iterator;

public class ModelTemplate
{ 
 /*
 * Set the top navigation bar as to show the functionalities associated with
 * the user roles   
 */   
 public static void setNavBar(Iterator<Privilege> privileges, Model model)
  {
    while(privileges.hasNext())
    { 
      Privilege auth=((Privilege) privileges.next());
      //check wheter the user is administrator or superadministrator and activate the related functionalities
      if(auth.getType().equals("Access") && auth.getPriority()<=2)
          model.addAttribute("userPage", "Manage Users");
      if(auth.getType().equals("Access") && auth.getPriority()<=1)
          model.addAttribute("managePlatform", "Manage Platform");
    }
  }
}
