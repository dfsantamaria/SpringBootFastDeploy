package it.unict.spring.application.exception.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

public class MultipleUsersFoundException extends Exception
{
    public MultipleUsersFoundException(String string)
    {
        super(string);
    }    
}
