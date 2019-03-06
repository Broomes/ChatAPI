package net.broomes.dao;

import net.broomes.model.User;
import net.broomes.service.ChatUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    private ChatUserDetailService chatUserDetailService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserDao(ChatUserDetailService chatUserDetailService, BCryptPasswordEncoder passwordEncoder){
        this.chatUserDetailService = chatUserDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserDetails> getUser(String username){
        UserDetails user = chatUserDetailService.loadUserByUsername(username);
        if(user==null){return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);}
        else {return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);}
    }

    public ResponseEntity<List> getUsers(){
        List<User> users = chatUserDetailService.loadUsers();
        if(users==null){return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);}
        else {return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);}
    }

    public ResponseEntity<User> saveUser(User user){

        // authorities to be granted
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(chatUserDetailService.userExists(user.getUsername())){
            return new ResponseEntity<>(null ,new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        else{
            chatUserDetailService.createUser(user);
            return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
        }
    }

    public ResponseEntity<String> deleteUser(String username){
        if(chatUserDetailService.userExists(username)){
            chatUserDetailService.deleteUser(username);
            return new ResponseEntity<>("User deleted successfully", new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("User not located in database", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
