/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.dto.user;

import com.sun.istack.NotNull;

/**
 *
 * @author danie
 */
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
