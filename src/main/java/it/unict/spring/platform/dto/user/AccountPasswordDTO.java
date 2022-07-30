package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import com.sun.istack.NotNull;
import it.unict.spring.platform.validation.PasswordMatches;
import it.unict.spring.platform.validation.ValidPassword;


@PasswordMatches
public class AccountPasswordDTO
{         
    @NotNull
    @ValidPassword
    private String password;
    @NotNull
    private String confirmPassword;
    
    
    public AccountPasswordDTO(String password, String confirmPassword)
    {     
      this.password = password;
      this.confirmPassword = confirmPassword;
    }
    
    public String getPassword()
    {
      return this.password;
    }
    
    public String getConfirmPassword()
    {
      return this.confirmPassword;
    }   
        
    public void setPassword(String password)
    {
      this.password = password;
    }
    
    public void setConfirmPassword(String password)
    {
      this.confirmPassword = password;
    }
}
