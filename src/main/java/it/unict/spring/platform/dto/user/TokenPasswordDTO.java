package it.unict.spring.platform.dto.user;

import com.sun.istack.NotNull;
import javax.validation.Valid;
import lombok.Data;

@Data
public class TokenPasswordDTO
{
   @NotNull 
   String token;
   @Valid
   AccountPasswordDTO password;
}
