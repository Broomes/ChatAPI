package net.broomes.dao;

import net.broomes.model.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ProfileDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Profile loadProfileByUsername(String username){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Profile profile = session.get(Profile.class, username);
        session.getTransaction().commit();
        session.close();
        return profile;
    }

    public List<Profile> loadProfiles() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Profile> profiles = session.createQuery("from Profile").getResultList();
        session.getTransaction().commit();
        session.close();
        return profiles;
    }

    public void createProfile(Profile profile) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(profile);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteProfile(String username) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Profile where username= :username");
        query.setParameter("username", username);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
