package net.broomes.controller;

import net.broomes.repository.ProfileRepository;
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

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping(path="/profiles")
    public ResponseEntity<List> getProfiles(){
        return new ResponseEntity<>(profileRepository.findAll(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path="/profile/{username}")
    public ResponseEntity<byte[]> getProfile(@PathVariable String username){
        byte[] avatar = profileRepository.findById(username).get().getAvatar();
        return new ResponseEntity<>(avatar, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(path="/profile")
    public ResponseEntity<String> deleteProfile(@RequestBody String username){

        //Converts @RequestBody JSON String into a string containing only username
        username = new JSONObject(username).getString("username");
        profileRepository.deleteById(username);
        return new ResponseEntity<>(username + " Deleted", new HttpHeaders(), HttpStatus.OK);
    }
}
