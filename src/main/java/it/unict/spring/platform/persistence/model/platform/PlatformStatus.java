package it.unict.spring.platform.persistence.model.platform;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity(name="platform_status")
@Table(name = "platform_status", catalog = "platform")
@Data
public class PlatformStatus implements Serializable
{
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = false)
  private boolean maintenanceMode;
  public PlatformStatus()
  {
        super();
        this.maintenanceMode = false;
  }
   public PlatformStatus(boolean maintenance)
  {
        super();
        this.maintenanceMode = maintenance;
  }
}
