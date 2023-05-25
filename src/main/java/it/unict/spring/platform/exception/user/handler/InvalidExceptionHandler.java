package it.unict.spring.platform.exception.user.handler;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidExceptionHandler
{
     @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex)
        {
         JSONObject obj=new JSONObject();   
         ArrayList<String> messages= new ArrayList();   
         for(ObjectError error : ex.getAllErrors())
         {
             messages.add(error.getDefaultMessage());
         }
         obj.put("status", "failed");
         obj.put("errors", new JSONArray(messages));
         return new ResponseEntity(obj.toString(), HttpStatus.BAD_REQUEST);
        } 
}
