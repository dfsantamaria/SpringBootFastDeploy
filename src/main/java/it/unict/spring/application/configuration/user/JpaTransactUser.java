package it.unict.spring.application.configuration.user;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 */

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager",
        basePackages = {"it.unict.spring.application.persistence.repository.user"}
        )
public class JpaTransactUser
{

    @Bean(name = "userEntityManager")   
    public LocalContainerEntityManagerFactoryBean getServersEntityManager(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("userSource") DataSource serversDataSource){
        return builder
                .dataSource(serversDataSource)
                .packages("it.unict.spring.application.persistence.model.user")
                .persistenceUnit("userUnit")
                .properties(additionalJpaProperties())
                .build();

    }

    Map<String,?> additionalJpaProperties()
    {
        Map<String,String> map = new HashMap<>();

        map.put("hibernate.hbm2ddl.auto", "update");        
        //map.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        map.put("hibernate.show_sql", "true");
        return map;
    }


    @Bean("userDataSourceProperties")    
    @ConfigurationProperties("app.datasource.user")
    public DataSourceProperties dataDataSourceProperties()
    {
        return new DataSourceProperties();
    }


    @Bean("userSource")    
    @ConfigurationProperties("app.datasource.user")
    public DataSource getDataSource(@Qualifier("userDataSourceProperties") DataSourceProperties dataDataSourceProperties) {
        return dataDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "userTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("userEntityManager") EntityManagerFactory serversEntityManager)
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(serversEntityManager);
        return transactionManager;
    }
}

/*
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager",
        basePackages = {"it.unict.spring.application.repository.user"}
        )

public class JpaTransactUser
{
    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getServersEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("userSource") DataSource dataSource,
            @Qualifier("userJpaVendor") JpaVendorAdapter adapter)
    {        
         Properties properties = new Properties();         
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.scripts.action","create");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target", "create.sql");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.create-source","metadata");
         
         LocalContainerEntityManagerFactoryBean lem=new LocalContainerEntityManagerFactoryBean();
         lem.setPackagesToScan("it.unict.spring.application.persistence.model.user");
         lem.setJpaProperties(properties);
         lem.setJpaVendorAdapter(adapter);
         lem.setDataSource(dataSource);
         return lem;
    }
    
    @Bean("userJpaVendor")    
    public JpaVendorAdapter jpaVendorAdapter()
    {
     HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
     hibernateJpaVendorAdapter.setShowSql(true);
     hibernateJpaVendorAdapter.setGenerateDdl(true); //Auto creating scheme when true
     //hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);//Database type
     return hibernateJpaVendorAdapter;
    }
  
    
    @Bean(name = "userTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory serversEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(serversEntityManager);
        return transactionManager;
    }
}



/*
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "domainsEntityManager",
        transactionManagerRef = "domainsTransactionManager",
        basePackages = {"it.unict.spring.application.repository.user"}
        )
public class JpaTransactUser {

    @Bean(name = "domainsEntityManager")
    public LocalContainerEntityManagerFactoryBean getdomainsEntityManager(EntityManagerFactoryBuilder builder
    ,@Qualifier("domainsDataSource") DataSource domainsDataSource){

        return builder
                .dataSource(domainsDataSource)
                .packages("it.unict.spring.application.persistence.model.user")
                .persistenceUnit("domains")
                .properties(additionalJpaProperties())
                .build();

    }


    Map<String,?> additionalJpaProperties(){
        Map<String,String> map = new HashMap<>();

        map.put("hibernate.hbm2ddl.auto", "create");
        map.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        map.put("hibernate.show_sql", "true");

        return map;
    }


    @Bean("domainsDataSourceProperties")
    @ConfigurationProperties("app.datasource.domains")
    public DataSourceProperties domainsDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean("domainsDataSource")
    public DataSource domainsDataSource(@Qualifier("domainsDataSourceProperties") DataSourceProperties domainsDataSourceProperties) {
        return domainsDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "domainsTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("domainsEntityManager") EntityManagerFactory domainsEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(domainsEntityManager);

        return transactionManager;
    }

}
*/

/*
import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
 basePackages = {"it.unict.spring.application.repository.user"},
 entityManagerFactoryRef = "userEntityManagerFactory"
 ,transactionManagerRef = "userTransactionManager"
)
public class JpaTransactUser
{

    @Bean("userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(
                                @Qualifier("userSource") DataSource dataSource,                                
                                @Qualifier("userJpaVendor") JpaVendorAdapter adapter) 
    {
         Properties properties = new Properties();
         properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.scripts.action","create");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target", "create.sql");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.create-source","metadata"); 
         
         LocalContainerEntityManagerFactoryBean lem=new LocalContainerEntityManagerFactoryBean();
         lem.setPackagesToScan("it.unict.spring.application.persistence.model.user");
         lem.setJpaProperties(properties);
         lem.setJpaVendorAdapter(adapter);        
         lem.setDataSource(dataSource);
         return lem;
    }

    @Bean("userTransactionManagerPlat")
    public PlatformTransactionManager userTransactionManager(
      @Qualifier("userEntityManagerFactory") LocalContainerEntityManagerFactoryBean topicsEntityManagerFactory)
    {
        return new JpaTransactionManager(Objects.requireNonNull(topicsEntityManagerFactory.getObject()));
    }
    
    @Bean("userJpaVendor")    
    public JpaVendorAdapter jpaVendorAdapter()
    {
     HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
     hibernateJpaVendorAdapter.setShowSql(true);
     hibernateJpaVendorAdapter.setGenerateDdl(true); //Auto creating scheme when true
     hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);//Database type
     return hibernateJpaVendorAdapter;
    }
}*/