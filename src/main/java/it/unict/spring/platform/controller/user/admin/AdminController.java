package it.unict.spring.platform.controller.user.admin;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import it.unict.spring.platform.dto.user.UserPerPageDTO;
import it.unict.spring.platform.dto.user.UserSearchDTO;
import it.unict.spring.platform.persistence.model.user.UserAccount;
import it.unict.spring.platform.service.user.SecureTokenService;
import it.unict.spring.platform.service.user.UserService;
import it.unict.spring.platform.utility.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import it.unict.spring.platform.service.utility.SearchManagerService;
import java.util.List;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api/admin")
public class AdminController
{
   @Autowired
   UserService userService; 
   @Autowired
   SecureTokenService secureTokenService;
   @Autowired
   SearchManagerService searchManager;
   
   @GetMapping(value = "searchuser", consumes = {"application/json"})
   public ResponseEntity<String> searchUser( @RequestBody UserPerPageDTO userperpagedto,                                                                
                                   @AuthenticationPrincipal CustomUserDetails user                                   
                                   ) 
   {           
      Page<UserAccount> pages = searchManager.search(userService, userperpagedto.getPage(), userperpagedto.getUser());
      List<UserSearchDTO> results = userService.createUserSearchDTOFromPage(pages);
      return new ResponseEntity<>(results.toString(), HttpStatus.OK);
   }  
   
   
    @GetMapping(value = "upgradeUserRoleAtStaff")
    public ResponseEntity<String> upgradeUserRoleAtStaff(@AuthenticationPrincipal CustomUserDetails user,
                                      @RequestParam("token") String token, 
                                      @RequestParam("approve") boolean approve)
    {       
       Long id=secureTokenService.existsToken(token); 
       JSONObject obj=new JSONObject();
       if(id==0L)
       {           
          obj.put("status","failed");
          return new ResponseEntity<>(obj.toString(), HttpStatus.BAD_REQUEST);   
       }
       else
       {             
         userService.enableRoleStaffMember(id, token, approve);      
         obj.put("status","success");
         obj.put("approve", approve);
         return new ResponseEntity<>(obj.toString(), HttpStatus.OK);
       }         
    }    
}

                                      