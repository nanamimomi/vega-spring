package com.uvic.venus.controller;

import com.uvic.venus.model.UserInfo;
import com.uvic.venus.repository.UserInfoDAO;
import com.uvic.venus.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserInfoDAO userInfoDAO;

    @Autowired
    DataSource dataSource;

    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/fetchusers", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllUsers(){
        List<UserInfo> userInfoList = userInfoDAO.findAll();
        return ResponseEntity.ok(userInfoList);
    }

    @RequestMapping(value ="/enableuser", method = RequestMethod.GET)
    public ResponseEntity<?> enableUserAccount(@RequestParam String username, @RequestParam boolean enable){
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = manager.loadUserByUsername(username);

        User.UserBuilder builder = User.builder();
        builder.username(userDetails.getUsername());
        builder.password(userDetails.getPassword());
        builder.authorities(userDetails.getAuthorities());
        builder.disabled(!enable);

        manager.updateUser(builder.build());
        return ResponseEntity.ok("User Updated Successfully");
    }

    @RequestMapping(value ="/changerole", method = RequestMethod.GET)
    public ResponseEntity<?> changeRole(@RequestParam String username, @RequestParam String role){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        UserDetails userDetails = manager.loadUserByUsername(username);

        User.UserBuilder builder = User.builder();
        builder.username(userDetails.getUsername());
        builder.password(userDetails.getPassword());
        builder.authorities(authorities);
        builder.disabled(userDetails.isEnabled());

        manager.updateUser(builder.build());
        return ResponseEntity.ok("User Updated Successfully");
    }

    @PostMapping(value = "/handlefileupload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file){
        storageService.store(file);
        return ResponseEntity.ok("File uploaded Successfully");
    }

}
