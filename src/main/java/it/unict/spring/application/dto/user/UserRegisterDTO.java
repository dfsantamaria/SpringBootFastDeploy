package it.unict.spring.application.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import com.sun.istack.NotNull;


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
