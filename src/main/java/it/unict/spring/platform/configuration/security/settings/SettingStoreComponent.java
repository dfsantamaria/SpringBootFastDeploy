
package it.unict.spring.platform.configuration.security.settings;

import it.unict.spring.platform.utility.user.SettingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SettingStoreComponent
{
  private SettingStore store=new SettingStore();
  @Bean
  public SettingStore settingStore()
  {
   return store;
  }
}
