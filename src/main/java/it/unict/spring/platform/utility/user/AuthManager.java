/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.platform.utility.user;

import it.unict.spring.platform.persistence.model.user.Privilege;

/**
 *
 * @author danie
 */
public  class AuthManager
{    
  public static boolean isAtLeastAdmin(Privilege auth)
  {
    return auth.getType().equals("Access") && auth.getPriority()<=2;
  }
  
  public static boolean isAtLeastSuperadmin(Privilege auth)
  {
   return auth.getType().equals("Access") && auth.getPriority()<=1;
  }
  
  public static boolean isAtLeastStaff(Privilege auth)
  {
    return auth.getType().equals("Access") && auth.getPriority()<=3;
  }
}
