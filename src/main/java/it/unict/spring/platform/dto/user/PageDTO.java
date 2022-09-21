package it.unict.spring.platform.dto.user;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import java.util.ArrayList;
import lombok.Data;


@Data
public class PageDTO 
{
   int pagesNumber;
   int totalPages;
   int itemsNumber;
   int firstPage;   
   int pageSpan;
   ArrayList itemsSet;
   
   public boolean isNextEnabled()
   {
     return firstPage+pageSpan <= totalPages;
   }
   
   public boolean isPreviousEnabled()
   {
     return firstPage > pageSpan;
   }   
}
