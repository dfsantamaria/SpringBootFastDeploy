package it.unict.spring.platform.dto.user;


/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import com.sun.istack.NotNull;
import it.unict.spring.platform.validation.user.PasswordMatches;
import it.unict.spring.platform.validation.user.ValidPassword;
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
    
    public AccountPasswordDTO(String password, String confirmPassword)
    {     
      this.password = password;
      this.confirmPassword = confirmPassword;
    }    
}
