/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.dto.user;

import com.sun.istack.NotNull;
import it.unict.spring.application.validation.PasswordMatches;
import it.unict.spring.application.validation.ValidPassword;

/**
 *
 * @author danie
 */
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
