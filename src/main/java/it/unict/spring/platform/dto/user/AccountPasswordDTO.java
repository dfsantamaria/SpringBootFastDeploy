package it.unict.spring.platform.dto.user;


/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import javax.validation.constraints.NotNull;
import it.unict.spring.platform.validation.user.password.PasswordMatches;
import it.unict.spring.platform.validation.user.password.ValidPassword;
import lombok.Data;


@PasswordMatches
@Data
public class AccountPasswordDTO
{         
    @NotNull
    @ValidPassword
    private String password;
    @NotNull
    private String confirmPassword;    
    private String oldpassword;
    
    public AccountPasswordDTO()
    {}
    
    public AccountPasswordDTO(String password, String confirmPassword, String oldpassword)
    {     
      this.password = password;
      this.confirmPassword = confirmPassword;
      this.oldpassword = oldpassword;
    }   
    
    public AccountPasswordDTO(String password, String confirmPassword)
    {     
      this.password = password;
      this.confirmPassword = confirmPassword;
      this.oldpassword = null;
    }   
}
