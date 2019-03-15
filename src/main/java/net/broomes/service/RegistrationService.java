package net.broomes.service;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

//    @Value("classpath:basic_avatar.jpg")
//    File basicAvatar;
//
//    private JdbcUserDetailsManager jdbcUserDetailsManager;
//    private PasswordEncoder passwordEncoder;
//    private ProfileDao profileDao;
//
//    @Autowired
//    public RegistrationService(ProfileDao profileDao, PasswordEncoder passwordEncoder, JdbcUserDetailsManager jdbcUserDetailsManager){
//        this.profileDao = profileDao;
//        this.passwordEncoder = passwordEncoder;
//        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
//    }
//
//    public ResponseEntity<String> checkAndRegisterUser(String username, String password, String confirmPassword, MultipartFile avatar) {
//
//        // if statements to check that username/password are formatted correctly.
//        if(!password.equals(confirmPassword)){
//            return new ResponseEntity<>("Password and Confirm Password do not match.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
//        }
//
//        if(!password.matches("[a-zA-Z0-9]*") || !username.matches("[a-zA-Z0-9]*")){
//            return new ResponseEntity<>("Username and Password can only consist of letters and numbers.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
//        }
//
//        if(password.length()<4 || password.length()>20){
//            return new ResponseEntity<>("Password must be between 4 - 20 characters.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
//        }
//
//        if(username.length()<2 || username.length()>20){
//            return new ResponseEntity<>("Username must be between 2 - 20 characters.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
//        }
//
//        // no avatar submitted, then load the default avatar from the resources folder
//        if(avatar==null){
//            try {
//                FileInputStream input = new FileInputStream(basicAvatar.getPath());
//                avatar = new MockMultipartFile("fileItem", basicAvatar.getName(), "image/jpg", IOUtils.toByteArray(input));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(avatar.getSize() > 10000000){
//            return new ResponseEntity<>("Profile picture is too large. Has to be less than 10MB", new HttpHeaders(), HttpStatus.BAD_REQUEST);
//        }
//
//        // set all usernames to lowercase.
//        username = username.toLowerCase();
//
//        // authorities to be granted
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        if(jdbcUserDetailsManager.userExists(username)){
//            return new ResponseEntity<>("Username already taken", new HttpHeaders(), HttpStatus.BAD_REQUEST);
//        }
//        else{
//            String encodedPassword = passwordEncoder.encode(password);
//            org.springframework.security.core.userdetails.User newUser = new org.springframework.security.core.userdetails.User(username, encodedPassword, authorities);
//            jdbcUserDetailsManager.createUser(newUser);
//            addProfile(username, avatar);
//            return new ResponseEntity<>("Registration Complete", new HttpHeaders(), HttpStatus.OK);
//        }
//    }
//
//    private void addProfile(String username, MultipartFile avatar) {
//        Profile profile = new Profile();
//
//        try {
//            profile = new Profile(username, avatar.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        profileDao.createProfile(profile);
//    }
}
