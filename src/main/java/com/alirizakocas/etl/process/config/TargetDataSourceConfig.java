package com.alirizakocas.etl.process.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "targetEntityManagerFactory",
        transactionManagerRef = "targetTransactionManager",
        basePackages = {"com.alirizakocas.etl.process.repository.target", "com.alirizakocas.etl.process.model.entity"}
)
public class TargetDataSourceConfig {

    @Autowired
    Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.target.datasource")
    public DataSource targetDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.target.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.target.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.target.datasource.password"));

        return dataSource;
    }


    @Bean(name = "targetEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean targetEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(targetDataSource())
                .properties(hibernateProperties())
                .packages("com.alirizakocas.etl.process.repository.target", "com.alirizakocas.etl.process.model.entity")
                .persistenceUnit("PostgrePU")
                .build();
    }


    @Bean(name = "targetTransactionManager")
    public PlatformTransactionManager targetTransactionManager(@Qualifier("targetEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map hibernateProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("hibernate.show_sql", "false");
        return map;
    }
}
