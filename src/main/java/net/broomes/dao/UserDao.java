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
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    ChatUserDetailService chatUserDetailService;

    public UserDetails getUser(String username){
        System.out.println(">>>>>>>>>>>>>>>>>> inside DAO going to userdetails");
        UserDetails user = chatUserDetailService.loadUserByUsername(username);
        return user;
    }

    public List<User> getUsers(){
        return chatUserDetailService.loadUsers();
    }

    public ResponseEntity<String> saveUser(User user){

        // authorities to be granted
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(chatUserDetailService.userExists(user.getUsername())){
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        else{
            chatUserDetailService.createUser(user);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        }
    }

    public ResponseEntity<String> deleteUser(String username){
        if(chatUserDetailService.userExists(username)){
            chatUserDetailService.deleteUser(username);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
