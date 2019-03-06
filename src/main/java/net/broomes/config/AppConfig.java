package net.broomes.config;

import net.broomes.model.Room;
import net.broomes.model.User;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("net.broomes")
public class AppConfig {

    @Bean
    public SessionFactory roomSessionFactory() {
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
                .configure("room_hibernate.cfg.xml")
                .addAnnotatedClass(Room.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    @Bean
    public SessionFactory userSessionFactory() {
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
                .configure("security_hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        return sessionFactory;
    }
}
