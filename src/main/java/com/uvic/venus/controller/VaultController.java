package com.uvic.venus.controller;


import com.uvic.venus.model.SecretInfo;
import com.uvic.venus.model.UserInfo;
import com.uvic.venus.repository.SecretInfoDAO;
import com.uvic.venus.repository.UserInfoDAO;
import com.uvic.venus.repository.UserRepository;
import com.uvic.venus.storage.StorageService;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class VaultController {

    @Autowired
    UserInfoDAO userInfoDAO;

    @Autowired
    DataSource dataSource;

    @Autowired
    StorageService storageService;

    @Autowired
    SecretInfoDAO secretInfoDAO;

    @Autowired
    UserRepository userRepository;


    /*
        Users are able to:
            - Create a secret
            - Read a secret
            - Edit a secret
            - Delete/archive a secret
     */


    /*
        list out all the secrets owned (and shared with) by the users
     */
    @RequestMapping(value = "/allsecrets", method = RequestMethod.GET)
    public ResponseEntity<?> listAllSecrets(){
        List<SecretInfo> secretInfosList = secretInfoDAO.findAll();
        return ResponseEntity.ok(secretInfosList);
    }


    /*
        create a new secret
     */
    @RequestMapping(value = "/createnewsecret", method = RequestMethod.GET)
    public ResponseEntity<?> createNewSecret(@RequestParam String name, @RequestParam String content){
        SecretInfo secret = new SecretInfo(name,content);
        Date now = new Date();
        secret.setDateCreated(now);
        /*
            maybe print out the secret for users to confirm the correct info
         */
        storageService.store((MultipartFile) secret);
        System.out.println(secret);
        return ResponseEntity.ok(secret);
    }



}
