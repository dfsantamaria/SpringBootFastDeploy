/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.serviceinterface.user;

import it.unict.spring.application.persistence.model.user.SecureToken;
import it.unict.spring.application.persistence.model.user.UserAccount;
import java.util.List;

/**
 *
 * @author danie
 */

public interface SecureTokenServiceInterface
{
    List<SecureToken> findAll();
    SecureToken generateToken ();
    SecureToken save (SecureToken token);  
    void delete(SecureToken token);  
    void addUserToToken(UserAccount user, SecureToken token);
    List<SecureToken> findByUser(UserAccount user);
    
}
