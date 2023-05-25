package it.unict.spring.platform.dto.user;
import it.unict.spring.platform.dto.utility.PageDTO;
import lombok.Data;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */
@Data
public class UserPerPageDTO
{
   UserSearchDTO user;
   PageDTO page;
}
