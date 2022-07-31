package it.unict.spring.platform.utility.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import org.apache.maven.model.Developer;
import org.apache.maven.model.License;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.LoggerFactory;


 
public class PomReader
{
  private static final org.slf4j.Logger applogger = LoggerFactory.getLogger(PomReader.class);    
  private Model model;
  private HashMap<String, Object> forkInfo;
  
  public PomReader() 
  {
      MavenXpp3Reader reader = new MavenXpp3Reader();
      InputStream is=PomReader.class.getClassLoader().getResourceAsStream("pom.xml");
      try
      {         
        model = reader.read(is);         
      } 
      catch (FileNotFoundException ex)
      {
          applogger.error("Accessing pom error: "+ ex.toString() + " "+ is.toString());
      }
      catch (IOException | XmlPullParserException ex)
      {
          applogger.error("Parsing POM file error: "+ ex.toString());
      }     
      this.initInfo();
  }
  
  public String getArtifactId()
  {
    return this.getModel().getArtifactId();
  }
  
  public List<Developer> geteDevelopers()
  {
    return this.getModel().getDevelopers();
  }
  
  public String getVersion()
  {
    return this.getModel().getVersion();
  }
  
  public Model getModel()
  {
    return this.model;
  }
  
  public List<License> getLicence()
  {
    return this.model.getLicenses();
  }
  
  public HashMap<String,Object> getForkInfo()
  {
    return this.forkInfo;
  }
  
  private void initInfo()
  {
    forkInfo= new HashMap(){{
                                       put("project_name", getArtifactId());
                                       put("project_author", geteDevelopers().get(0).getName());
                                       put( "project_version", getVersion());
                                       put("project_license", getLicence().get(0).getName());                                       
                                       }}; 
    initFork();
  }
  
  /*
  * Don't touch the fork information, this is for authorship recognition. 
  * Just edit the pom file and add your information in the initInfo() method
  */
  private void initFork()
  {
    forkInfo.put("fork", new HashMap(){{
                                         put("fork_name", "SpringBootFastDeploy");
                                         put("fork_author", "Daniele Francesco Santamaria");
                                         put("fork_version", "1.0");
                                         put("fork_licence", "GNU General Public License, Version 3.0");
                                         put("fork_repository", "https://github.com/dfsantamaria/SpringBootFastDeploy");                                       
                                       }});
  }
  
  

  
}
