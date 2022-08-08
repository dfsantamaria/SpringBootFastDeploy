package it.unict.spring.platform.serviceinterface.data;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */



import it.unict.spring.platform.persistence.model.data.Data;
import java.util.List;
import java.util.Optional;


public interface DataServiceInterface
{
    List<Data> findAll();
    Optional<Data> findByName(String name);
    public Data save (Data g);    
    void delete(Data todo);
}

