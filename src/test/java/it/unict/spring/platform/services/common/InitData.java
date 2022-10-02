/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.platform.services.common;

import it.unict.spring.platform.exception.user.MultipleUsersFoundException;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.persistence.model.user.UserRegister;
import it.unict.spring.platform.service.user.UserRegisterService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.UserExpirationInformation;
import java.util.Optional;
import javax.transaction.Transactional;

/**
 *
 * @author danie
 */
@Transactional
public class InitData
{       
    
    public static void initUser(UserService userServ, UserRegisterService regServ,
                                 String username, String password, 
                                 String mail, String organization,
                                 String name, String middleName, String lastName) throws MultipleUsersFoundException
    {
       Optional<UserAccount> users; 
       users = userServ.findByUsername(username);
       if(users.isEmpty())
       {
          UserAccount user = userServ.getSuperAdminUser(username, password, mail,
                                                         UserExpirationInformation.getAccountExpirationDate(),
                                                                 UserExpirationInformation.getCredentialExpirationDate(),
                                                                 organization
                                                         );
          userServ.setEnabled(user, true);  
          userServ.save(user);
          
          UserRegister register=new UserRegister(name, middleName, lastName);
          userServ.addRegisterToUser(register, user);
          regServ.save(register);
       }
       
    } 
    
    public static void clearUser(UserService userServ, String username)
    {
      Optional<UserAccount> findusers = userServ.findByUsername(username);
      userServ.delete(findusers.get());
    }
    
}
