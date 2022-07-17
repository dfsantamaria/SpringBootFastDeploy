/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.persistence.model.user;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userregistry", catalog = "useraccount")
public class UserRegister implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstname;
    private String middlename;
    private String lastname;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName ="id")
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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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