package it.unict.spring.platform.configuration.user;

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
        basePackages = {"it.unict.spring.platform.persistence.repository.user"}
        )
public class JpaTransactUser
{

    @Bean(name = "userEntityManager")   
    public LocalContainerEntityManagerFactoryBean getServersEntityManager(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("userSource") DataSource serversDataSource){
        return builder
                .dataSource(serversDataSource)
                .packages("it.unict.spring.platform.persistence.model.user")
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
