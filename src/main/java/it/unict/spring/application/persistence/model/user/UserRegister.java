/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.persistence.model.user;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userregistry", catalog = "useraccount")
public class UserRegister implements Serializable
{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstname;
    private String middlename;
    private String lastname;
    
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserAccount user;
    
    public UserRegister()
    {
       super();
    }
    
    public UserRegister(String name, String middlename, String lastname)
    {
       super();
       this.firstname=name;       
       this.middlename=middlename;
       this.lastname=lastname;
    }
    
    public UserAccount getUser()
    {
     return user;
    }
    
    public void setUser(UserAccount user)
    {
      this.user = user;
      this.id =user.getId();              
    }

    public Long getId() {
        return id;
    }

        
    public String getFirstName()
    {
      return this.firstname;
    }
    
    public void setFirstName(String name)
    {
      this.firstname=name;
    }
    
    public String getMiddleName()
    {
      return this.middlename;
    }
    
    public void setMiddleName(String name)
    {
      this.middlename=name;
    }
    
    public String getLastName()
    {
      return this.lastname;
    }
    
    public void setLastName(String name)
    {
      this.lastname=name;
    }
}