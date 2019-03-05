package net.broomes.dao;

import net.broomes.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    private JdbcUserDetailsManager jdbcUserDetailsManager;
    private SessionFactory sessionFactory;


    @Autowired
    public UserDao(JdbcUserDetailsManager jdbcUserDetailsManager, SessionFactory sessionFactory){
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.sessionFactory = sessionFactory;
    }

    public ResponseEntity<String> saveUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hasedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hasedPassword);

        if(user.getAvatar()==null){
            String avatarString = "0x6210341fab6210341";
            byte[] avatarByteArray = avatarString.getBytes();
            user.setAvatar(avatarByteArray);
        }

        // authorities to be granted
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(jdbcUserDetailsManager.userExists(user.getUsername())){
//            request.setAttribute("registered", false);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        else{
            User newUser = new User(user.getUsername(), user.getPassword(), authorities, user.getAvatar());
            jdbcUserDetailsManager.createUser(newUser);
//            request.setAttribute("registered", true);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        }
    }

    public User getUser(String username){
        UserDetails loadedUser = jdbcUserDetailsManager.loadUserByUsername(username);
        User user = new User(loadedUser.getUsername(), loadedUser.getPassword(),loadedUser.getAuthorities(), new byte[5]);
       return user;
    }

    public List<User> getUsers(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public ResponseEntity<String> deleteUser(String username){
        //        Converts @RequestBody JSON String into a string containing only username
        username = new JSONObject(username).getString("username");

        if(jdbcUserDetailsManager.userExists(username)){
            jdbcUserDetailsManager.deleteUser(username);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
