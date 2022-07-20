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
    
    public String getFirstName()
    {
        return this.firstName;
    }
    
    public String getMiddleName()
    {
        return this.middleName;
    }
    
    public String getLastName()
    {
        return this.lastName;
    }
    
    public void setFirstName(String name)
    {
        this.firstName=name;
    }
    
    public void setMiddleName(String name)
    {
        this.middleName=name;
    }
    
    public void setLastName(String name)
    {
        this.lastName=name;
    }
}
