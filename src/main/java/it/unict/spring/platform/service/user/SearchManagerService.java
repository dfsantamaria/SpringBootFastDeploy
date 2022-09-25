package it.unict.spring.platform.service.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


import it.unict.spring.platform.dto.user.PageDTO;
import it.unict.spring.platform.serviceinterface.user.SearcheableInterface;
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
      int itemsNumb = Integer.parseInt(pageSearch.getItemsNumber());         
      if(pageSearch.getCurrentPage()<0)
      {
         int currentPage;
         if(pageSearch.getCurrentPage() == -1)          
           currentPage= pageSearch.getFirstPage()- pageSearch.getPageSpan(); 
         else  
           currentPage = pageSearch.getFirstPage()+pageSearch.getPageSpan();         
         pageSearch.setCurrentPage(currentPage);       
      } 
      
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
