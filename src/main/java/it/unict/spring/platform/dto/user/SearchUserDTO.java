package it.unict.spring.platform.dto.user;
/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

@Data
public class SearchUserDTO
{
    @NotNull
    String key;
    List<String> parameters;
    
    
    private static final ArrayList<String> userRegOptions = new ArrayList<>(Arrays.asList(
            "firstname",
            "middlename",
            "lastname"));
    private static final ArrayList<String> userAccountOptions = new ArrayList<>(Arrays.asList(
            "username",
            "mail"));
    
    private static final ArrayList<String> orgOptions = new ArrayList<>(Arrays.asList(
            "organization"));
            
    
     private static final ArrayList<String> userRegOptionNames = new ArrayList<>(Arrays.asList(
            "First Name",
            "Middle Name",
            "Last Name"));
     
     private static final ArrayList<String> userAccountOptionNames = new ArrayList<>(Arrays.asList(     
            "User Name",
            "E-Mail"));
     
    private static final ArrayList<String> orgOptionNames = new ArrayList<>(Arrays.asList(
            "Organization"));      
    
    
    public static  ArrayList<String> getUserRegOptions()
    {
      return userRegOptions;
    }
     
    public static  ArrayList<String> getUserRegOptionNames()
    {
      return userRegOptionNames;
    } 
    
    public static  ArrayList<String> getUserAccountOptions()
    {
      return userAccountOptions;
    }
     
    public static  ArrayList<String> getUserAccountOptionNames()
    {
      return userAccountOptionNames;
    } 
    
    public static  ArrayList<String> getOrgOptions()
    {
      return orgOptions;
    }
     
    public static  ArrayList<String> getOrgOptionNames()
    {
      return orgOptionNames;
    } 
    
    public static ArrayList getOptions()
    {
        ArrayList ret= new ArrayList<>();
        ret.addAll(userRegOptions);
        ret.addAll(userAccountOptions);
        ret.addAll(orgOptions);
        return ret;        
    }
    
    public static ArrayList getOptionNames()
    {
      ArrayList ret= new ArrayList<>();
        ret.addAll(userRegOptionNames);
        ret.addAll(userAccountOptionNames);
        ret.addAll(orgOptionNames);
        return ret; 
    }
    
    public static List<Pair<String,String>> getOptionPairs()
    { 
      List<Pair<String,String>> values = new ArrayList<>();       
      for (int i=0; i<userRegOptionNames.size(); i++)      
        values.add(Pair.of(userRegOptions.get(i), userRegOptionNames.get(i)));
      
      for (int i=0; i<userAccountOptionNames.size(); i++)      
        values.add(Pair.of(userAccountOptions.get(i), userAccountOptionNames.get(i)));
      
      for (int i=0; i<orgOptionNames.size(); i++)      
        values.add(Pair.of(orgOptions.get(i), orgOptionNames.get(i)));
      
      return values;
    }    
        
    public SearchUserDTO(String key, List<String> parameters)
    {
      this.key=key;
      this.parameters=parameters;      
    }  
}