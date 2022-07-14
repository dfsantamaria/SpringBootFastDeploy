/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.exception.user;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author danie
 */
public class UserCredentialsExpiredException extends DataAccessException
{
    public UserCredentialsExpiredException(String string)
    {
        super("User credentials are expired: "+ string);
    }       
}
