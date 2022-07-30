package it.unict.spring.platform.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


public class UserNotFoundException extends Exception
{
    public UserNotFoundException(String string)
    {
        super(string);
    }    
}
