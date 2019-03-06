package net.broomes.controller;

import net.broomes.dao.UserDao;
import net.broomes.model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class UserController {

    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDao){
        this.userDao = userDao;
    }

    @GetMapping(path="/users")
    public ResponseEntity<List> getUsers(){
        return userDao.getUsers();
    }

    @GetMapping(path="/user/{username}")
    public ResponseEntity<UserDetails> getUser(@PathVariable String username){
        return userDao.getUser(username);
    }

    @PostMapping(path="/user")
    public ResponseEntity<User> processRegister(@RequestBody User user) {
        return userDao.saveUser(user);
    }

    @DeleteMapping(path="/user")
    public ResponseEntity<String> deleteUser(@RequestBody String username){

        //Converts @RequestBody JSON String into a string containing only username
        username = new JSONObject(username).getString("username");
        return userDao.deleteUser(username);
    }
}
