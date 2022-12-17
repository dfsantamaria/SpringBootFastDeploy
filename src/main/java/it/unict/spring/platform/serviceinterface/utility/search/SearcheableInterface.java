package it.unict.spring.platform.serviceinterface.utility.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


public interface SearcheableInterface<T,D>
{
   Page<T> searchFromDTO(D searchdto, Pageable pageable);    
}
