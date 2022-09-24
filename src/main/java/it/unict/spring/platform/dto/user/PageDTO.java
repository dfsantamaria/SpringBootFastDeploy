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
   private int firstPage;   //the first page of the current page span
   private int pageSpan; //number of page button to be shown
   private int currentPage; //the current page
   
   public boolean isNextDisabled()
   {
     return firstPage+pageSpan >= totalPages;
   }
   
   public boolean isPreviousDisabled()
   {
     return firstPage <= pageSpan;
   }  
   
}
