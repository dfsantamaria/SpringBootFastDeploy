package it.unict.spring.platform.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


public class MultipleUsersFoundException extends Exception
{
    public MultipleUsersFoundException(String string)
    {
        super(string);
    }    
}
