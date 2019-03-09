package net.broomes.controller;

import net.broomes.dao.ProfileDao;
import net.broomes.model.Profile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class UserController {

    @Value("classpath:basic_avatar.jpg")
    File fileItem;

    private JdbcUserDetailsManager jdbcUserDetailsManager;
    private PasswordEncoder passwordEncoder;
    private ProfileDao profileDao;

    @Autowired
    public UserController(ProfileDao profileDao, PasswordEncoder passwordEncoder, JdbcUserDetailsManager jdbcUserDetailsManager){
        this.profileDao = profileDao;
        this.passwordEncoder = passwordEncoder;
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
    }


    @PostMapping(path="/register")
    public ResponseEntity<String> processRegister(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam(name="avatar", required =false) MultipartFile avatar) throws IOException {

        if(!password.equals(confirmPassword) || username.length()<2 || username.length()>20){
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
        }

        if(avatar==null){
            avatar=getBasicAvatar();
        }

        // authorities to be granted
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(jdbcUserDetailsManager.userExists(username)){
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.ALREADY_REPORTED);
        }
        else{
            String encodedPassword = passwordEncoder.encode(password);
            org.springframework.security.core.userdetails.User newUser = new org.springframework.security.core.userdetails.User(username, encodedPassword, authorities);
            jdbcUserDetailsManager.createUser(newUser);
            Profile profile = new Profile(username, avatar.getBytes());
            profileDao.createProfile(profile);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        }
    }

    /**
     * Helper class needed to convert image file to MultipartFile
     * @return avatar MultipartFile
     * @throws IOException
     */
    private MultipartFile getBasicAvatar() throws IOException {

        FileInputStream input = new FileInputStream(fileItem.getPath());
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                fileItem.getName(), "image/jpg", IOUtils.toByteArray(input));
        return multipartFile;
    }
}
