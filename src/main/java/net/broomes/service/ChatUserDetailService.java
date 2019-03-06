package net.broomes.service;

import net.broomes.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatUserDetailService implements UserDetailsService {

    private SessionFactory userSessionFactory;

    @Autowired
    public ChatUserDetailService(SessionFactory userSessionFactory){
        this.userSessionFactory = userSessionFactory;
    }

    public boolean userExists(String username) {
        if(loadUserByUsername(username) != null){ return true; }
        else { return false; }
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        Session session = userSessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, username);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public List<User> loadUsers() {
        Session session = userSessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public void createUser(User user) {
        Session session = userSessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUser(String username) {
        Session session = userSessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from User where username= :username");
        query.setParameter("username", username);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
