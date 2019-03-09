package net.broomes.controller;

import net.broomes.dao.ProfileDao;
import net.broomes.model.Profile;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ProfileController {

    private ProfileDao profileDao;

    @Autowired
    public ProfileController(ProfileDao profileDao){
        this.profileDao = profileDao;
    }

    @GetMapping(path="/profiles")
    public List<Profile> getProfiles(){
        return profileDao.loadProfiles();
    }

    @GetMapping(path="/profile/{username}")
    public ResponseEntity<byte[]> getProfile(@PathVariable String username){
        byte[] avatar = profileDao.loadProfileByUsername(username).getAvatar();
        return new ResponseEntity<>(avatar, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(path="/profile")
    public ResponseEntity<String> deleteProfile(@RequestBody String username){

        //Converts @RequestBody JSON String into a string containing only username
        username = new JSONObject(username).getString("username");
        profileDao.deleteProfile(username);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
