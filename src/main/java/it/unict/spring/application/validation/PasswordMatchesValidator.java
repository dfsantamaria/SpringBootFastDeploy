/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unict.spring.application.validation;

import it.unict.spring.application.dto.user.UserAccountDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author danie
 */
public class PasswordMatchesValidator
  implements ConstraintValidator<PasswordMatches, Object> {
    
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context)
    {
        UserAccountDTO user = (UserAccountDTO) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}