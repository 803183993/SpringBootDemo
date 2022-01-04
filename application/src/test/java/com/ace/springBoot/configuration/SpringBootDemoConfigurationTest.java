package com.ace.springBoot.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpringBootDemoConfigurationTest
{
    @Test
    public void shouldLoadConfiguration()
    {
        DatasourceConfiguration expectedEatasourceConfiguration = new DatasourceConfiguration();
        expectedEatasourceConfiguration.setDriverClassName("someDriverClassName");
        expectedEatasourceConfiguration.setUrl("someUrl");
        expectedEatasourceConfiguration.setUsername("someUsername");
        expectedEatasourceConfiguration.setPassword("somePassword");

        SpringBootDemoConfiguration configuration = new SpringBootDemoConfiguration();
        configuration.setEnvironment("someEnvironment");
        configuration.setDatasource(expectedEatasourceConfiguration);

        assertEquals("someEnvironment", configuration.getEnvironment());
        DatasourceConfiguration actualDatasourceConfiguration = configuration.getDatasource();
        assertEquals(expectedEatasourceConfiguration.getDriverClassName(), actualDatasourceConfiguration.getDriverClassName());
        assertEquals(expectedEatasourceConfiguration.getUrl(), actualDatasourceConfiguration.getUrl());
        assertEquals(expectedEatasourceConfiguration.getUsername(), actualDatasourceConfiguration.getUsername());
        assertEquals(expectedEatasourceConfiguration.getPassword(), actualDatasourceConfiguration.getPassword());
    }
}