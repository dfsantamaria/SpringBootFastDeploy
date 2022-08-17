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
public class OrganizationDTO
{
    @NotNull    
    private String name;  
    
    public OrganizationDTO(String name)
    {
     this.name = name;
    }    
}
