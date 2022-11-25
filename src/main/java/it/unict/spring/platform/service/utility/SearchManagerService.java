package it.unict.spring.platform.service.utility;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.dto.utility.PageDTO;
import it.unict.spring.platform.serviceinterface.utility.search.SearcheableInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import it.unict.spring.platform.utility.inferface.SearchDTOInterface;

@Data
@AllArgsConstructor
@Service
public class SearchManagerService<T>
{    
    public Page<T> search(SearcheableInterface searchService, PageDTO pageSearch, SearchDTOInterface searchdto)
    {
      int itemsNumb = pageSearch.getItemsNumber();         
          
      Page<T> pages = null;
      try
      {
       pages = searchService.searchFromDTO(searchdto, PageRequest.of(pageSearch.getCurrentPage()-1, itemsNumb));        
      }
      catch(UnsupportedOperationException  exception)
      {
       pages = searchService.searchFromDTO(searchdto, PageRequest.of(0, itemsNumb));      
       pageSearch.setCurrentPage(0);
      }  
      
      pageSearch.setTotalPages(pages.getTotalPages());     
      return pages;
    }
}
