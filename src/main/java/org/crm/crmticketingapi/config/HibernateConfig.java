package org.crm.crmticketingapi.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class HibernateConfig {

    private final DataSource dataSource;



    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.format_sql}")
    private String formatSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String ddlAuto;






    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        LocalSessionFactoryBean sessionFactory =
                new LocalSessionFactoryBean();

        sessionFactory.setDataSource(
                dataSource
        );

        sessionFactory.setPackagesToScan(
                "org.crm.crmticketingapi.entity"
        );

        Properties properties =
                new Properties();

        properties.put(
                "hibernate.dialect",
                dialect
        );

        properties.put(
                "hibernate.show_sql",
                showSql
        );

        properties.put(
                "hibernate.format_sql",
                formatSql
        );

        properties.put(
                "hibernate.hbm2ddl.auto",
                ddlAuto
        );

        sessionFactory.setHibernateProperties(
                properties
        );

        return sessionFactory;
    }


}