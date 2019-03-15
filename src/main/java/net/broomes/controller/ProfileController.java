package net.broomes.controller;

import net.broomes.service.ProfileService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping(path="/profiles")
    public ResponseEntity<List> getProfiles(){
        return profileService.getProfiles();
    }

    @GetMapping(path="/profile/{username}")
    public ResponseEntity<byte[]> getProfile(@PathVariable String username){
        return profileService.getProfile(username);
    }

    @DeleteMapping(path="/profile")
    public ResponseEntity<String> deleteProfile(@RequestBody String username){

        //Converts @RequestBody JSON String into a string containing only username
        username = new JSONObject(username).getString("username");
        return profileService.deleteProfile(username);
    }
}
