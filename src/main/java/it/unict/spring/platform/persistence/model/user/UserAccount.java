package it.unict.spring.platform.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

@Entity(name="UserAccount")
@Table(name = "useraccount", catalog = "useraccount")
@Data
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
    
    private Timestamp credentialExpire;
    
    private boolean isAccountNonLocked;
    
    private boolean isSuspended;
    
    private Timestamp accountExpire;
        
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @JoinTable(name = "user_to_privileges", catalog = "useraccount",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "privilege_id",  referencedColumnName = "id", nullable = false)})
    private Set<Privilege> privileges= new HashSet<>();

    
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @JoinTable(name = "user_to_organizations", catalog = "useraccount",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "organization_id",  referencedColumnName = "id", nullable = false)})
    private Set<Organization> organizations= new HashSet<>();

    
    @OneToOne(mappedBy ="user", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Setter(AccessLevel.NONE)
    private UserRegister register;   
    
    @OneToMany(mappedBy ="tokenId.user", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    private Set<SecureToken> tokens = new HashSet<>();
    
    
    @OneToOne(mappedBy ="user", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserLogin login;   
    //

    public UserAccount() {
         super();
         this.isEnabled=false;        
         this.isAccountNonLocked=true; 
         this.isSuspended=false;
    }
    
    public UserAccount(String username, String password, String mail, Timestamp accountExpire, Timestamp credentialExpire) {
        super();
        this.username=username;
        this.password=password;         
        this.mail=mail;     
        this.isEnabled = false;
        this.credentialExpire=credentialExpire;
        this.isAccountNonLocked=true;
        this.accountExpire = accountExpire;
        this.isSuspended = false;
    }
   
     public void addPrivileges(Privilege priv)
     {     
      this.privileges.add(priv);
      priv.addUser(this);
     }
    
    public void addOrganization(Organization org)
    {      
      this.organizations.add(org);
      org.addUser(this);
    }
    
    public void addSecureToken(SecureToken token)
    {        
      this.tokens.add(token);
      token.addUser(this);
    }
        
    public void removePrivileges(Privilege priv)
    {     
      this.privileges.remove(priv);
     }
    
    public void removeOrganization(Organization org)
    {      
      this.organizations.remove(org);
    }
    
    public void removeSecureToken(SecureToken token)
    {
      this.tokens.remove(token);
      token.removeUser();
    }    
     
    //

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(id).append(", username=").append(username).append(", privileges=").append("]");
        return builder.toString();
    }
    
    public boolean isCredentialsNonExpired()
    {
         return this.credentialExpire.after(Timestamp.valueOf(LocalDateTime.now()));
    }
    
    public boolean isAccountNonLocked()
    {
       return this.isAccountNonLocked;    
    }

    public boolean isAccountNonExpired()
    {
      return this.accountExpire.after(Timestamp.valueOf(LocalDateTime.now()));
    }
    
    public boolean isAccountSuspended()
    {
      return this.isSuspended;
    }
          
    public void setRegister(UserRegister register)
    {
      this.register=register;
      register.setUser(this);
    }
    
    public void setLogin(UserLogin login)
    {
       this.login=login;
       login.setUser(this);
    }
    
    @Override
    public int hashCode() 
    {        
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
         
        if (!(obj instanceof UserAccount)) 
            return false;
         
        return this.id != null && id.equals(((UserAccount) obj).getId());
   }
}