package it.unict.spring.application.service.data;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import it.unict.spring.application.persistence.model.data.Data;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import it.unict.spring.application.persistence.repository.data.DataRepository;
import it.unict.spring.application.serviceinterface.data.DataServiceInterface;


@Service
public class DataService implements DataServiceInterface
{
    @Autowired
    DataRepository repository;

           
    @Override
    public List<Data> findAll()
    {
        return (List<Data>) repository.findAll();
    }
    
    @Override
    public List<Data> findByName(String name)
    { 
      return repository.findByName(name);
    }
    
    @Override
    public Data save (Data g)
    {
      return repository.save(g);
    }
    
    @Override
    public void delete(Data data)
    {
      repository.delete(data);
    }
}