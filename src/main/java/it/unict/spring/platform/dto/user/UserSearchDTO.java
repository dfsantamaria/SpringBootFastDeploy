package it.unict.spring.platform.dto.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import lombok.Data;


@Data
public class UserSearchDTO
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
    return id==null || firstName==null || middleName==null ||
           lastName==null || mail==null || username==null || orgname == null;
  }
}