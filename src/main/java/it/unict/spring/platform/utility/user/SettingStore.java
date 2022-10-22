
package it.unict.spring.platform.utility.user;

public class SettingStore
{
   private boolean maintenance;
   
   public SettingStore()
   {
     maintenance=false;
   }
   
   public boolean getMaintenanceMode()
   {
     return maintenance;
   }
   
   public boolean toggleMaintenanceMode()
   {
     maintenance = !maintenance;
     return maintenance;
   }
}
