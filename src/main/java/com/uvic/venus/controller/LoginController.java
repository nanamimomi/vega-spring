package com.uvic.venus.controller;

import com.uvic.venus.auth.JwtUtil;
import com.uvic.venus.model.AuthenticationRequest;
import com.uvic.venus.model.AuthenticationResponse;
import com.uvic.venus.model.RegisterUserInfo;
import com.uvic.venus.model.UserInfo;
import com.uvic.venus.repository.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserInfoDAO userInfoDAO;

    //@Autowired
    //UserRepository userRepository;

    @GetMapping
    public Principal reteievePrincipal(Principal principal) {
        return principal;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        System.out.println("Entered into createAuthenticationRequests");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException badCredentialsException){
            System.out.println("Unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Found");
        }
        JdbcUserDetailsManager user = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = user.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        Object[] authorities = userDetails.getAuthorities().toArray();

        return ResponseEntity.ok(new AuthenticationResponse(jwt, authorities));
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserInfo user) throws Exception{
        JdbcUserDetailsManager dataManager = new JdbcUserDetailsManager(dataSource);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        //User user1 = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), authorities);
        User.UserBuilder builder = User.builder();
        builder.disabled(true);
        builder.passwordEncoder(passwordEncoder::encode);
        builder.password(user.getPassword());
        builder.username(user.getUsername());
        builder.authorities(authorities);
        dataManager.createUser(builder.build());

        UserInfo userinfo = new UserInfo(user.getUsername(), user.getFirstname(), user.getLastname());
        System.out.println(userinfo);
        userInfoDAO.save(userinfo);

        return ResponseEntity.ok("User Created Successfully");
    }


}
