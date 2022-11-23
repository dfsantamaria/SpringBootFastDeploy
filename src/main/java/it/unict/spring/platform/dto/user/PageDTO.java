package it.unict.spring.platform.dto.user;
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
   private String itemsNumber; //number of results to be shown for each page
  
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
     return (this.getPageSpan() <= this.getTotalPages()? this.getPageSpan() : this.getTotalPages());     
   }
   
   public int getFirstPage()
   {
     int count = 1;
     while(count<this.getCurrentPage())
         count+=this.getPageSpan();
     return count;
   }
   
}
