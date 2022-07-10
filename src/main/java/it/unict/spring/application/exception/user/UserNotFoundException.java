/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.exception.user;

/**
 *
 * @author danie
 */
public class UserNotFoundException extends Exception
{
    public UserNotFoundException(String string)
    {
        super(string);
    }    
}
