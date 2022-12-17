/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.platform.utility.user;

import it.unict.spring.platform.persistence.model.user.Privilege;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

public  class AuthManager
{    
  private static final HashMap<String, Object> privilegeMap =new HashMap(){{
                                       put("ROLE_SUPERADMIN", 1.0);
                                       put("ROLE_ADMIN", 2.0);
                                        put("ROLE_STAFF", 3.0);
                                         put("ROLE_STANDARDUSER", 4.0);
  }};
  
  public static double getSuperAdminPriority(){return (double) privilegeMap.get("ROLE_SUPERADMIN");}
  public static double getAdminPriority(){return (double) privilegeMap.get("ROLE_ADMIN");}
  public static double getStaffPriority(){return (double) privilegeMap.get("ROLE_STAFF");}
  public static double getStandardUserPriority(){return (double) privilegeMap.get("ROLE_STANDARDUSER");}
  
  public static boolean isAtLeastAdmin(Privilege auth)
  {
    return auth.getType().equals("Access") && auth.getPriority()<= AuthManager.getAdminPriority();
  }
  
  public static boolean isAtLeastAdmin(Iterator<Privilege> privileges )
  {
    while(privileges.hasNext())
    {   
      if(AuthManager.isAtLeastAdmin(privileges.next()))
          return true;
    }
    return false;
  }
  
  public static boolean isAtLeastSuperadmin(Privilege auth)
  {
   return auth.getType().equals("Access") && auth.getPriority()<= AuthManager.getSuperAdminPriority();
  }
  
  public static boolean isAtLeastStaff(Privilege auth)
  {
    return auth.getType().equals("Access") && auth.getPriority()<= AuthManager.getStaffPriority();
  }
}
