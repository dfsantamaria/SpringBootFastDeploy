package it.unict.spring.platform.dto.user;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import com.sun.istack.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class SearchUserDTO
{
    @NotNull
    String key;
    List<String> parameters;
    
    public SearchUserDTO(String key, List<String> parameters)
    {
      this.key=key;
      this.parameters=parameters;      
    }  
}