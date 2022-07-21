package it.unict.spring.application.configuration.data;

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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "dataEntityManager",
        transactionManagerRef = "dataTransactionManager",
        basePackages = {"it.unict.spring.application.persistence.repository.data"}
        )
public class JpaTransactData
{

    @Bean(name = "dataEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean getServersEntityManager(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("dataSource") DataSource serversDataSource){
        return builder
                .dataSource(serversDataSource)
                .packages("it.unict.spring.application.persistence.model.data")
                .persistenceUnit("dataUnit")
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


    @Bean("dataDataSourceProperties")
    @Primary
    @ConfigurationProperties("app.datasource.data")
    public DataSourceProperties dataDataSourceProperties()
    {
        return new DataSourceProperties();
    }


    @Bean("dataSource")
    @Primary
    @ConfigurationProperties("app.datasource.data")
    public DataSource getDataSource(@Qualifier("dataDataSourceProperties") DataSourceProperties dataDataSourceProperties) {
        return dataDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "dataTransactionManager")
    @Primary
    public JpaTransactionManager transactionManager(@Qualifier("dataEntityManager") EntityManagerFactory serversEntityManager)
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(serversEntityManager);
        return transactionManager;
    }
}
/*
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
                 entityManagerFactoryRef = "dataEntityManagerFactory",
                 transactionManagerRef = "dataTransactionManager",
                 basePackages = {"it.unict.spring.application.repository.data"}
                      )

public class JpaTransactData
{
    @Bean(name = "dataEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean getServersEntityManager
        (
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource,
                                   @Qualifier("dataJpaVendor") JpaVendorAdapter adapter)
    {        
         Properties properties = new Properties();        
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.scripts.action","create");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target", "create.sql");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.create-source","metadata");
         
         LocalContainerEntityManagerFactoryBean lem=new LocalContainerEntityManagerFactoryBean();
         lem.setPackagesToScan("it.unict.spring.application.persistence.model.data");
         lem.setJpaProperties(properties);
         lem.setJpaVendorAdapter(adapter);
         lem.setDataSource(dataSource);
         return lem;
    }
    @Bean("dataJpaVendor")
    @Primary
    public JpaVendorAdapter jpaVendorAdapter()
    {
     HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
     hibernateJpaVendorAdapter.setShowSql(true);
     hibernateJpaVendorAdapter.setGenerateDdl(true); //Auto creating scheme when true
     //hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);//Database type    
     return hibernateJpaVendorAdapter;
    }


    @Bean(name = "dataTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("dataEntityManagerFactory") EntityManagerFactory serversEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager(); 
        transactionManager.setEntityManagerFactory(serversEntityManager);
        return transactionManager;
    }
    
}

/*
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
  basePackages = {"it.unict.spring.application.repository.data"},
  entityManagerFactoryRef = "dataEntityManagerFactory",
  transactionManagerRef = "dataTransactionManager"
)
public class JpaTransactData
{

    @Bean("dataEntityManagerFactory") 
    @Primary
    public LocalContainerEntityManagerFactoryBean dataEntityManagerFactory(
                                @Qualifier("dataSource") DataSource dataSource,                                
                                @Qualifier("dataJpaVendor") JpaVendorAdapter adapter)
    {        
         Properties properties = new Properties();
         properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.scripts.action","create");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target", "create.sql");
         properties.put("spring.jpa.properties.javax.persistence.schema-generation.create-source","metadata");
         
         LocalContainerEntityManagerFactoryBean lem=new LocalContainerEntityManagerFactoryBean();
         lem.setPackagesToScan("it.unict.spring.application.persistence.model.data");
         lem.setJpaProperties(properties);
         lem.setJpaVendorAdapter(adapter);
         lem.setDataSource(dataSource);
         return lem;
    }

    @Bean("dataTransactionManagerPlatform")
    @Primary    
    public PlatformTransactionManager dataTransactionManager(
    @Qualifier("dataEntityManagerFactory") LocalContainerEntityManagerFactoryBean topicsEntityManagerFactory)
    {
        return new JpaTransactionManager(Objects.requireNonNull(topicsEntityManagerFactory.getObject()));
    }
    
    @Bean("dataJpaVendor")
    @Primary
    public JpaVendorAdapter jpaVendorAdapter()
    {
     HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
     hibernateJpaVendorAdapter.setShowSql(true);
     hibernateJpaVendorAdapter.setGenerateDdl(true); //Auto creating scheme when true
     hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);//Database type    
     return hibernateJpaVendorAdapter;
    }
}
*/



/*
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "serversEntityManager",
        transactionManagerRef = "serversTransactionManager",
        basePackages = {"it.unict.spring.application.repository.data"}
        )
public class JpaTransactData {

    @Bean(name = "serversEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean getServersEntityManager(EntityManagerFactoryBuilder builder,
                                                                          @Qualifier("serversDataSource") DataSource serversDataSource){


        return builder
                .dataSource(serversDataSource)
                .packages("it.unict.spring.application.persistence.model.data")
                .persistenceUnit("servers")
                .properties(additionalJpaProperties())
                .build();

    }

    Map<String,?> additionalJpaProperties(){
        Map<String,String> map = new HashMap<>();

        map.put("hibernate.hbm2ddl.auto", "create");
        map.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        map.put("hibernate.show_sql", "true");

        return map;
    }


    @Bean("serversDataSourceProperties")
    @Primary
    @ConfigurationProperties("app.datasource.servers")
    public DataSourceProperties serversDataSourceProperties(){
        return new DataSourceProperties();
    }



    @Bean("serversDataSource")
    @Primary
    @ConfigurationProperties("app.datasource.servers")
    public DataSource serversDataSource(@Qualifier("serversDataSourceProperties") DataSourceProperties serversDataSourceProperties) {
        return serversDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "serversTransactionManager")
    @Primary
    public JpaTransactionManager transactionManager(@Qualifier("serversEntityManager") EntityManagerFactory serversEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(serversEntityManager);

        return transactionManager;
    }
}
*/