/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.persistence.repository.user;

import it.unict.spring.application.persistence.model.user.UserRegister;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author danie
 */


@Repository
public interface UserRegisterRepository extends JpaRepository<UserRegister, Long> 
{
    @Override
    List<UserRegister> findAll();
    @Override 
    UserRegister save(UserRegister register);   
    @Override
    void delete(UserRegister register); 
}
