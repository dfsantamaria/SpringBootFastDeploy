package it.unict.spring.platform.utility.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class UserExpirationInformation 
{
   private static Timestamp maxAccount=Timestamp.valueOf(LocalDateTime.now().plusYears(100));
   private static Timestamp maxCredentials=Timestamp.valueOf(LocalDateTime.now().plusYears(100));
                   
   public static Timestamp getAccountExpirationDate() 
   {
     return maxAccount;                                                       
   }
   
   public static Timestamp getCredentialExpirationDate() 
   {
     return maxCredentials;                                                       
   }
}
