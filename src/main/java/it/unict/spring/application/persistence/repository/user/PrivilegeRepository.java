package it.unict.spring.application.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.persistence.model.user.Privilege;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> 
{
    List<Privilege> findByName(String name);
    @Override
    List<Privilege> findAll();
    @Override 
    Privilege save(Privilege privilege);
    @Override
    void delete(Privilege todo);
}