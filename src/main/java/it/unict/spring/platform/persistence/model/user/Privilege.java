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
import lombok.Data;

@Entity(name="Privilege")
@Table(name = "privilege", catalog = "useraccount")
@Data
public class Privilege implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = false)
    private double priority;
    
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

    public Privilege(String name, String label, String type, double priority) {
        super();
        this.name = name;
        this.description = label;
        this.type = type;
        this.priority = priority;
    }

    public void addUser(UserAccount user)
    {
      this.users.add(user);
    }
    
    public void deleteUser(UserAccount user)
    {
      this.users.remove(user);
    }
    
    
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