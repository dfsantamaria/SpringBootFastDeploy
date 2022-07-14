/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.exception.user;

import org.springframework.dao.DataAccessException;

public class UserNotEnabledException extends DataAccessException
{
    public UserNotEnabledException(String string)
    {
        super("User is not enabled: "+ string);
    }    
}
