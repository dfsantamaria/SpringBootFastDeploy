package it.unict.spring.platform.persistence.repository.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.persistence.model.user.Privilege;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> 
{
    Optional<Privilege> findOneByName(String name);
    @Override
    List<Privilege> findAll();
    @Override 
    Privilege save(Privilege privilege);
    @Override
    void delete(Privilege todo);
    List<Privilege> findAllByTypeAndPriorityLessThanAndPriorityGreaterThan(String type, Double priorityStart, Double priorityEnd);
    Optional<Privilege> findOneByUsers_IdAndType(Long id, String type);
}