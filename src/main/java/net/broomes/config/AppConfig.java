package net.broomes.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.broomes.model.Room;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableWebMvc
@ComponentScan("net.broomes")
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    @Bean
    public DataSource securityDataSource(){

        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
        try {
            securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        securityDataSource.setUser(env.getProperty("jdbc.user"));
        securityDataSource.setPassword(env.getProperty("jdbc.password"));

        securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return securityDataSource;
    }

    /**
     * Helper method that converts environment property from String to int.
     * @param propertyName name of environment property to be converted
     * @return the int value of the property
     */
    private int getIntProperty(String propertyName){
        String stringPropertyValue = env.getProperty(propertyName);
        return Integer.parseInt(stringPropertyValue);
    }
}