/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.persistence.repository.user;

import it.unict.spring.application.persistence.model.user.SecureToken;
import it.unict.spring.application.persistence.model.user.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author danie
 */
@Repository
public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> 
{    
 @Override
 List<SecureToken> findAll();
 
 List<SecureToken> findByUser(UserAccount user);
 @Override 
 SecureToken save(SecureToken user);   
 @Override
 void delete(SecureToken user);  
    
}
