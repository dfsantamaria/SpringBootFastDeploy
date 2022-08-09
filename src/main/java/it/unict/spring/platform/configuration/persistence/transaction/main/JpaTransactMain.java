package it.unict.spring.platform.configuration.persistence.transaction.main;

/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
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
        entityManagerFactoryRef = "mainDataEntityManager",
        transactionManagerRef = "mainTransactionManager",
        basePackages = {"it.unict.spring.platform.persistence.repository.data", 
                        "it.unict.spring.platform.persistence.repository.user"}
        )
public class JpaTransactMain
{

    @Bean(name = "mainDataEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean getServersEntityManager(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("mainDataSource") DataSource serversDataSource){
        return builder
                .dataSource(serversDataSource)
                .packages("it.unict.spring.platform.persistence.model.data", 
                "it.unict.spring.platform.persistence.model.user")
                .persistenceUnit("mainUnit")
                .properties(additionalJpaProperties())
                .build();

    }

    Map<String,?> additionalJpaProperties()
    {
        Map<String,String> map = new HashMap<>();

        map.put("hibernate.hbm2ddl.auto", "update"); 
        //map.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        map.put("hibernate.show_sql", "true");
        // required to create che secondary schema useraccount
        //see https://www.javafixing.com/2022/03/fixed-create-schema-if-does-not-exist.html
        map.put("javax.persistence.create-database-schemas", "true");
        return map;
    }


    @Bean("mainDataSourceProperties")
    @Primary
    @ConfigurationProperties("app.datasource.main")
    public DataSourceProperties mainDataSourceProperties()
    {
        return new DataSourceProperties();
    }


    @Bean("mainDataSource")
    @Primary
    @ConfigurationProperties("app.datasource.main")
    public DataSource getDataSource(@Qualifier("mainDataSourceProperties") DataSourceProperties dataDataSourceProperties) {
        return mainDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "mainTransactionManager")
    @Primary
    public JpaTransactionManager transactionManager(@Qualifier("mainDataEntityManager") EntityManagerFactory serversEntityManager)
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(serversEntityManager);
        return transactionManager;
    }
}