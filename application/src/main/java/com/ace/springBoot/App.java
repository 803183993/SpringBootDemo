package com.ace.springBoot;

import com.ace.springBoot.configuration.DatasourceConfiguration;
import com.ace.springBoot.configuration.SpringBootDemoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import static java.lang.String.*;

@SpringBootApplication
@EnableConfigurationProperties(SpringBootDemoConfiguration.class)
public class App
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private SpringBootDemoConfiguration configuration;

    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(App.class);
        app.setLazyInitialization(true);
        app.run();
    }

    @Bean
    @SuppressWarnings("unused")
    public DataSource datasource()
    {
        DatasourceConfiguration datasource = configuration.getDatasource();
        LOGGER.info(format("Loading datasource with URL [%s]", datasource.getUrl()));

        return DataSourceBuilder.create()
            .driverClassName(datasource.getDriverClassName())
            .url(datasource.getUrl())
            .username(datasource.getUsername())
            .password(datasource.getPassword())
            .build();
    }
}