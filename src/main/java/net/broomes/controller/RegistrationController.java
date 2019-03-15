package net.broomes.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api")
public class RegistrationController {

//    private RegistrationService registrationService;
//
//    @Autowired
//    public RegistrationController(RegistrationService registrationService){
//        this.registrationService = registrationService;
//    }
//
//    @PostMapping(path="/register")
//    public ResponseEntity<String> processRegister(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam(name="avatar", required =false) MultipartFile avatar) {
//        return registrationService.checkAndRegisterUser(username, password, confirmPassword, avatar);
//    }
}
