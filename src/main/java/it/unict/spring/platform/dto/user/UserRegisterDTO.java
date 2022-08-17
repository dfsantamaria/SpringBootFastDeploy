package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserRegisterDTO
{
    @NotNull
    String firstName;
    String middleName;
    @NotNull
    String lastName;
    
    public UserRegisterDTO(String fname, String mname, String lname)
    {
      this.firstName=fname;
      this.middleName=mname;
      this.lastName=lname;
    }  
}
