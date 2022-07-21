package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import com.sun.istack.NotNull;

public class OrganizationDTO
{
    @NotNull    
    private String name;  
    
    public OrganizationDTO(String name)
    {
     this.name = name;
    }
            
    public String getName()
    {
      return this.name;
    }
    
    public void setName(String name)
    {
      this.name = name;
    }
}
