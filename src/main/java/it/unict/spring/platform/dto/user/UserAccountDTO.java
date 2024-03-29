package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import lombok.Data;

//@PasswordMatches
@Data
public class UserAccountDTO
{    
    @NotNull    
    private String username;   
    @NotNull   
    private String mail;      
    @Valid    
    private AccountPasswordDTO password;    
    double role=0.0;
    
    public UserAccountDTO(String username, String mail, String password, String confirmPassword, double role)
    {
      this.username=username;
      this.mail = mail;
      this.password=new AccountPasswordDTO(password, confirmPassword);
      this.role=role;      
    }      
    
}
