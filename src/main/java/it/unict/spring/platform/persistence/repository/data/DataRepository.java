package it.unict.spring.platform.persistence.repository.data;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.persistence.model.data.Data;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> 
{
    Optional<Data> findByName(String name);
    @Override
    List<Data> findAll();
    @Override
    Data save(Data data);
    @Override
    void delete(Data data);
}