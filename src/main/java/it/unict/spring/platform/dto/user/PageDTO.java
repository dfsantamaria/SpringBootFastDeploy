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
   private int pagesNumber;
   private int totalPages;
   private String itemsNumber;
   private int firstPage;   
   private int pageSpan; 
   private int currentPage;
   
   public boolean isNextEnabled()
   {
     return firstPage+pageSpan <= totalPages;
   }
   
   public boolean isPreviousEnabled()
   {
     return firstPage > pageSpan;
   }   
}
