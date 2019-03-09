package net.broomes.controller;

import net.broomes.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("api")
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping(path="/register")
    public ResponseEntity<String> processRegister(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam(name="avatar", required =false) MultipartFile avatar) {
        return registrationService.checkAndRegisterUser(username, password, confirmPassword, avatar);
    }
}
