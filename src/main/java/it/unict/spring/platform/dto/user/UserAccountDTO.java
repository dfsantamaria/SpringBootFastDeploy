package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import com.sun.istack.NotNull;
import it.unict.spring.platform.validation.PasswordMatches;
import it.unict.spring.platform.validation.ValidPassword;


@PasswordMatches
public class UserAccountDTO
{    
    @NotNull    
    private String username;   
    @NotNull   
    private String mail;  
    @NotNull
    @ValidPassword
    private String password;
    @NotNull
    private String confirmPassword;
    
    
    public UserAccountDTO(String username, String mail, String password, String confirmPassword)
    {
      this.username=username;
      this.mail = mail;
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
    
    public String getMail()
    {
      return this.mail;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public void setUsername(String username)
    {
      this.username = username;
    }
    
    public void setMail(String mail)
    {
      this.mail = mail;
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
