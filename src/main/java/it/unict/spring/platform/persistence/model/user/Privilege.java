package it.unict.spring.platform.persistence.model.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="Privilege")
@Table(name = "privilege", catalog = "useraccount")
public class Privilege implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false, updatable = false)
    private String type;
    
    @ManyToMany(mappedBy= "privileges")    
    private Set<UserAccount> users = new HashSet<>();
    

    public Privilege() {
        super();
    }

    public Privilege(String name, String label, String type) {
        super();
        this.name = name;
        this.description = label;
        this.type = type;
    }

    public void addUser(UserAccount user)
    {
      this.users.add(user);
    }
    
    public void deleteUser(UserAccount user)
    {
      this.users.remove(user);
    }
    
    public String getDescription()
    {
      return this.description;
    }
    
    public void setDescription(String description)
    {
      this.description = description;
    }
    
    public String getType()
    {
     return this.type;
    }
    
    public void setType(String type)
    {
      this.type=type;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set <UserAccount> getUsers() {
        return users;
    }

    public void setUsers(final Set<UserAccount> users) {
        this.users = users;
    }
    //

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Privilege [id=").append(id).append(", name=").append(name).append("]");
        return builder.toString();
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
         
        if (!(obj instanceof Privilege)) 
            return false;
         
        return this.id != null && id.equals(((Privilege) obj).getId());
   }

}