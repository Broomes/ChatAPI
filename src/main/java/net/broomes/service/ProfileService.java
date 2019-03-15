package net.broomes.service;

import net.broomes.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ResponseEntity<byte[]> getProfile(String username){
        if(profileRepository.findById(username).isPresent()) {
            byte[] avatar = profileRepository.findById(username).get().getAvatar();
            return new ResponseEntity<>(avatar, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List> getProfiles(){
        return new ResponseEntity<>(profileRepository.findAll(),  new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteProfile(String username) {
        if(profileRepository.findById(username).isPresent()){
            profileRepository.deleteById(username);
            return new ResponseEntity<>(username + " Deleted", new HttpHeaders(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Profile does not exist", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
