
package it.unict.spring.platform.specifications.user;

import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.Organization;
import it.unict.spring.platform.persistence.model.user.UserAccount;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserAccountSpecs
{
  public static Specification<UserAccount> getUserAccountByData(String username, String mail, String firstname, 
                                                                String middlename, String lastname, String orgname)
  {
      return new Specification<UserAccount>()
      {
          @Override
          public Predicate toPredicate(Root<UserAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
          {
             Predicate predicate=criteriaBuilder.conjunction();
              
             if(username!=null && !username.isEmpty())                             
               predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("username").as(String.class), "%"+username+"%")); 
                            
             if(mail!=null && !mail.isEmpty())
               predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("mail").as(String.class), "%"+mail+"%")); 
                            
             if(firstname!=null && !firstname.isBlank())              
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("register").get("firstname").as(String.class), "%"+firstname+"%"));   
              
             if(middlename!=null && !middlename.isBlank())              
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("register").get("middlename").as(String.class), "%"+middlename+"%"));   
             
             if(lastname!=null && !lastname.isBlank())              
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("register").get("lastname").as(String.class), "%"+lastname+"%"));   
             
             if(orgname!=null && !orgname.isBlank())              
             {
                Join<UserAccount, Organization> orgs = root.join("organizations");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(orgs.get("name").as(String.class), "%"+orgname+"%"));
                
             }
             return predicate;
          }
      };
  }    
}
