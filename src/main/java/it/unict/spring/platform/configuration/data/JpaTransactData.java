package it.unict.spring.platform.configuration.data;

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
        entityManagerFactoryRef = "dataEntityManager",
        transactionManagerRef = "dataTransactionManager",
        basePackages = {"it.unict.spring.platform.persistence.repository.data"}
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
                .packages("it.unict.spring.platform.persistence.model.data")
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