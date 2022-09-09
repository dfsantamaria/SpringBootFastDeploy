package it.unict.spring.platform.persistence.model.user;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

@Entity(name="PersistentLogin")
@Table(name = "persistent_logins", catalog = "data")
@Data
public class PersistentLogin implements Serializable
{
  
    @Id
    private String series;
    private String username;
    private String token;
    private Timestamp last_used;  
}