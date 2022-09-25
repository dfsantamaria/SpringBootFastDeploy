package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import lombok.Data;
import it.unict.spring.platform.utility.inferface.SearchDTOInterface;


@Data
public class UserSearchDTO implements SearchDTOInterface
{
  String id;  
  String firstName;
  String middleName;
  String lastName;
  String mail;
  String username;
  String orgname;
  
  public UserSearchDTO()
  {
  }
  
  public boolean allNullFields()
  {
    return (firstName.isBlank() && middleName.isBlank() &&
           lastName.isBlank() && mail.isBlank() && username.isBlank() && orgname.isBlank());
  }
}