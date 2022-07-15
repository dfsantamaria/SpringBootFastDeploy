package it.unict.spring.application.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

@Entity
@Table(name = "useraccount", catalog = "useraccount")
public class UserAccount implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String mail;
    
    private String password;
    
    private boolean isEnabled;
    
    private boolean isCredentialsNonExpired;
    
    private boolean isAccountNonLocked;
    
    private boolean isAccountNonExpired;
        
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @JoinTable(name = "user_to_privileges",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "privilege_id",  referencedColumnName = "id", nullable = false)})
    private Set<Privilege> privileges= new HashSet<>();

    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @JoinTable(name = "user_to_organizations",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "organization_id",  referencedColumnName = "id", nullable = false)})
    private Set<Organization> organizations= new HashSet<>();

    @OneToMany(mappedBy ="user")
    private Set<SecureToken> tokens = new HashSet<>();
    
    //

    public UserAccount() {
         super();
         this.isEnabled=false;
         this.isCredentialsNonExpired=true;
         this.isAccountNonLocked=true;
         this.isAccountNonExpired=true;
         
    }
    
    public UserAccount(String username, String password, String mail) {
        super();
        this.username=username;
        this.password=password;         
        this.mail=mail;     
        this.isEnabled = false;
        this.isCredentialsNonExpired=true;
        this.isAccountNonLocked=true;
        this.isAccountNonExpired=true;
    }
    //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void addPrivileges(Privilege priv)
    {
      priv.addUser(this);
      this.privileges.add(priv);
     }
    
    public void addOrganization(Organization org)
    {      
      this.organizations.add(org);
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

     public String getMail() {
        return mail;
    }

    public boolean isEnabled(){
        return isEnabled;
    } 
    
    public void setEnabled(boolean enabled)
    {
      this.isEnabled = enabled;
    }
     
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Set<Organization> getOrganization()
    {
        return organizations;
    }

    public void setOrganization(Set<Organization> organization)
    {
        this.organizations = organization;
    }

    //

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(id).append(", username=").append(username).append(", privileges=").append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());        
        result = (prime * result) + ((password == null) ? 0 : password.hashCode());        
        result = (prime * result) + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserAccount other = (UserAccount) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }        
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    public boolean isCredentialsNonExpired()
    {
         return this.isCredentialsNonExpired;
    }

    public boolean isAccountNonLocked()
    {
       return this.isAccountNonLocked;    
    }

    public boolean isAccountNonExpired()
    {
      return this.isAccountNonExpired;
    }
    
    public void setIsCredentialsNonExpired(boolean val)
    {
      this.isCredentialsNonExpired=val;
    }

    public void setIsAccountNonLocked(boolean val)
    {
       this.isAccountNonLocked=val;    
    }

    public void setIsAccountNonExpired(boolean val)
    {
      this.isAccountNonExpired=val;
    }    
}