package com.andreyermolenko.spring.rest.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
//аналог applicationContext
@Configuration
@ComponentScan(basePackages = "com.andreyermolenko.spring.rest")
@EnableWebMvc
@EnableTransactionManagement
public class MyConfig {

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSourse = new ComboPooledDataSource();
        try {
            dataSourse.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSourse.setJdbcUrl("jdbc:mysql://localhost:3306/my_db?useSSL=false");
            dataSourse.setUser("bestuser");
            dataSourse.setPassword("bestuser");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSourse;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactor() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.andreyermolenko.spring.rest.entity");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager =
                new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactor().getObject());
        return transactionManager;
    }

}

