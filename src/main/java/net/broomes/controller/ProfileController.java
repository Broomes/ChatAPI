package net.broomes.controller;

import net.broomes.dao.ProfileDao;
import net.broomes.entity.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("api")
public class ProfileController {

    private static Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private SessionFactory factory;

    @GetMapping("/profiles")
    public List<Profile> getProfiles(){
        Session session = factory.getCurrentSession();
        ProfileDao profileDao = new ProfileDao();
        try {
            List<Profile> profiles = profileDao.getProfiles(session);
            session.close();
            return profiles;
        } catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @GetMapping("/profile/{username}")
    public Profile getProfile(@PathVariable String username){
        Session session = factory.getCurrentSession();
        ProfileDao profileDao = new ProfileDao();
        try {
            Profile profile = profileDao.getProfile(session, username);
            session.close();
            return profile;
        } catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @PutMapping(path="/profile", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> saveProfile(@RequestBody Profile newProfile){
        Session session = factory.getCurrentSession();
        Profile profile = new Profile(newProfile.getUsername(), newProfile.getAvatar());
        ProfileDao profileDao = new ProfileDao();
        try {
            profileDao.saveProfile(session, profile);
            session.close();
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/profile", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteProfile(@RequestBody String username){
        Session session = factory.getCurrentSession();

//        Converts @RequestBody JSON String into a string containing only username
        username = new JSONObject(username).getString("username");

        ProfileDao profileDao = new ProfileDao();

        try {
            profileDao.deleteProfile(session, username);
            session.close();
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
