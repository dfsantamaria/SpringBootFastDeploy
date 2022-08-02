package it.unict.spring.platform.persistence.model.data;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.persistence.model.user.UserAccount;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "data", catalog = "data")
public class Data implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
  
    
    /*
     @ManyToOne(targetEntity = UserAccount.class,  cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
     @PrimaryKeyJoinColumn
     private UserAccount user;
    */

    public Data()
    {
        super();
    }

    public Data(String name) {
        super();
        this.name = name;
    }

    //

    
   
            
    
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

    //

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Entry [id=").append(id).append(", name=").append(name).append("]");
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
         
        if (!(obj instanceof Data)) 
            return false;
         
        return this.id != null && id.equals(((Data) obj).getId());
   }

}