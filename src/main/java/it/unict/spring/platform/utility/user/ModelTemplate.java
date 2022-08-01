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
 
 public static void setNavBar(Iterator<Privilege> privileges, Model model)
  {
    while(privileges.hasNext())
    { 
      Privilege auth=((Privilege) privileges.next());
      if(auth.getType().equals("Access") && auth.getId()<=1)
          model.addAttribute("userPage", "Manage Users");
    }
  }
}
