/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.exception.user;

public class MultipleUsersFoundException extends Exception
{
    public MultipleUsersFoundException(String string)
    {
        super(string);
    }    
}
