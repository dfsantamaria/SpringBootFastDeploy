package it.unict.spring.platform.dto.utility;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import lombok.Data;


@Data
public class PageDTO 
{   
   private int totalPages; //number of Page get from the Search
   private int itemsNumber; //number of results to be shown for each page  
   private int pageSpan; //number of page button to be shown
   private int currentPage; //the current page
   
   public boolean isNextDisabled()
   {
     if (totalPages==currentPage)
         return true;
     return getFirstPage()+pageSpan >= totalPages;
   }
   
   public boolean isPreviousDisabled()
   {
     if(currentPage<=1)
         return true;
     return getFirstPage() <= pageSpan;
   } 
   
   public int getCurrentSpan()
   {
     if(this.getTotalPages()==0)
         return 1;
     return  this.getFirstPage() + Math.min(this.getPageSpan(), getTotalPages()-getFirstPage()+1) -1;   
   }
   
   public int getFirstPage()
   { 
     int shift= getPageSpan(); 
     if((getCurrentPage() % getPageSpan()) !=0)
         shift = (getCurrentPage() % getPageSpan());
     return getCurrentPage() - shift +1;
   }

   public int getItemStep()
   {
     if(currentPage==1)
         return 1;
     return (currentPage-1)*(getPageSpan()+1);
   }
   
}
