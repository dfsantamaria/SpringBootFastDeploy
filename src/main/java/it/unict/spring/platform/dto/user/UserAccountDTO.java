package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import com.sun.istack.NotNull;
import it.unict.spring.platform.validation.PasswordMatches;
import javax.validation.Valid;


//@PasswordMatches
public class UserAccountDTO
{    
    @NotNull    
    private String username;   
    @NotNull   
    private String mail;      
    @Valid    
    private AccountPasswordDTO password;
    
    
    public UserAccountDTO(String username, String mail, String password, String confirmPassword)
    {
      this.username=username;
      this.mail = mail;
      this.password=new AccountPasswordDTO(password, confirmPassword);      
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
    
    public AccountPasswordDTO getPassword()
    {
      return this.password;
    }
    
    public void setPassword(AccountPasswordDTO password)
    {
      this.password = password;
    }    
}
