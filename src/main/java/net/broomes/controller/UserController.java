package net.broomes.controller;

import net.broomes.dao.UserDao;
import net.broomes.model.User;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("api")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserDao userDao;

    @GetMapping(path="/users")
    public List<User> getUsers(){
        try {
            return userDao.getUsers();
        } catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @GetMapping(path="/user/{username}")
    public UserDetails getUser(@PathVariable String username){
        try {
            return userDao.getUser(username);
        } catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @PostMapping(path="/user")
    public ResponseEntity<String> processRegister(@RequestBody User user) {
        return userDao.saveUser(user);
    }

    @DeleteMapping(path="/user")
    public ResponseEntity<String> deleteUser(@RequestBody String username){
        //        Converts @RequestBody JSON String into a string containing only username
        username = new JSONObject(username).getString("username");

        return userDao.deleteUser(username);
    }
}
