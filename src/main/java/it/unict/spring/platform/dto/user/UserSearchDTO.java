package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;


@Data
public class UserSearchDTO
{
  String firstName;
  String middleName;
  String lastName;
  String mail;
  String username;
  String orgname;
  public UserSearchDTO()
  {
  }
}