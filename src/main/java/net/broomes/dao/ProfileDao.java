package net.broomes.dao;

import net.broomes.entity.Profile;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileDao {

    public void saveProfile(Session session, Profile profile){
        session.beginTransaction();
        session.save(profile);
        session.getTransaction().commit();
    }

    public Profile getProfile(Session session, String username){
        session.beginTransaction();
        Profile theProfile = session.get(Profile.class, username);
        session.getTransaction().commit();
        return theProfile;
    }

    public List<Profile> getProfiles(Session session){
        session.beginTransaction();
        List<Profile> profiles = session.createQuery("from Profile").getResultList();
        session.getTransaction().commit();
        return profiles;
    }

    public void deleteProfile(Session session, String username){
        session.beginTransaction();
        Query query = session.createQuery("delete from Profile where username= :username");
        query.setParameter("username", username);
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
