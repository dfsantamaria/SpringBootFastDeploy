package it.unict.spring.platform.dto.user;

import com.sun.istack.NotNull;
import javax.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenPasswordDTO
{
   @NotNull 
   private String token;
   @Valid
   private AccountPasswordDTO password;
   
   
}
