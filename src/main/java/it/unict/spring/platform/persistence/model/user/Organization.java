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


@Entity(name="Organization")
@Table(name = "organization", catalog = "useraccount")
@Data
public class Organization implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy= "organizations")    
    private Set<UserAccount> users = new HashSet<>();

    public Organization() {
        super();
    }

    public Organization(String name) {
        super();
        this.name = name;
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
        builder.append("Organization [id=").append(id).append(", name=").append(name).append("]");
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
         
        if (!(obj instanceof Organization)) 
            return false;
         
        return this.id != null && id.equals(((Organization) obj).getId())
                               && name.equals(((Organization) obj).getName());
   }
}