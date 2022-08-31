package com.alirizakocas.etl.process.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "sourceEntityManagerFactory",
        transactionManagerRef = "sourceTransactionManager",
        basePackages = {"com.alirizakocas.etl.process.repository.source", "com.alirizakocas.etl.process.model.entity"}
)
public class SourceDataSourceConfig {

    @Autowired
    Environment env;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.source.datasource")
    public DataSource sourceDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.source.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.source.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.source.datasource.password"));

        return dataSource;
    }

    @Primary
    @Bean(name = "sourceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sourceEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(sourceDataSource())
                .properties(hibernateProperties())
                .packages("com.alirizakocas.etl.process.repository.source", "com.alirizakocas.etl.process.model.entity")
                .persistenceUnit("PostgrePU")
                .build();
    }

    @Primary
    @Bean(name = "sourceTransactionManager")
    public PlatformTransactionManager sourceTransactionManager(@Qualifier("sourceEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map hibernateProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("hibernate.show_sql", "false");
        return map;
    }
}
