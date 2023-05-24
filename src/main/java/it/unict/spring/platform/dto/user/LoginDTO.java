/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.platform.dto.user;

import com.sun.istack.NotNull;
import it.unict.spring.platform.validation.user.password.ValidPassword;
import lombok.Data;

@Data
public class LoginDTO
{
  @NotNull
  @ValidPassword
  private String password;  
  @NotNull
  String username;  
}
