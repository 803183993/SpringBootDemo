package com.ace.springBoot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo")
public class SpringBootDemoConfiguration
{
    private String environment;
    private DatasourceConfiguration datasource;

    public void setEnvironment(String environment)
    {
        this.environment = environment;
    }

    public void setDatasource(DatasourceConfiguration datasource)
    {
        this.datasource = datasource;
    }

    public String getEnvironment()
    {
        return environment;
    }

    public DatasourceConfiguration getDatasource()
    {
        return datasource;
    }
}
