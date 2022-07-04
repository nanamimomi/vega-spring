package com.uvic.venus.controller;


import com.uvic.venus.model.SecretInfo;
import com.uvic.venus.model.UserInfo;
import com.uvic.venus.repository.SecretInfoDAO;
import com.uvic.venus.repository.UserInfoDAO;
import com.uvic.venus.repository.UserRepository;
import com.uvic.venus.storage.StorageService;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/secrets")
public class VaultController {

    @Autowired
    UserInfoDAO userInfoDAO;

    //@Autowired
    //DataSource dataSource;

    //@Autowired
    //StorageService storageService;

    @Autowired
    SecretInfoDAO secretInfoDAO;

    //@Autowired
    //UserRepository userRepository;


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
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> listAllSecrets(){
        List<SecretInfo> secretInfoList = secretInfoDAO.findAll();
        return ResponseEntity.ok(secretInfoList);
    }
    /*
        create a new secret
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createNewSecret(@RequestParam String name, @RequestParam String content, @RequestParam File file){
        SecretInfo secret = new SecretInfo(name,content);
        if (file != null)
            secret.setFile(file);

        Date now = new Date();
        secret.setDateCreated(now);
        /*
            maybe print out the secret for users to confirm the correct info
         */
        secretInfoDAO.save(secret);
        System.out.println(secret);
        return ResponseEntity.ok(secret);
    }

    /*
        update a secret
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<?> updateSecret(SecretInfo secret, @RequestParam String name, @RequestParam String content, @RequestParam File file){
        SecretInfo newSecret = new SecretInfo(name, content);
        newSecret.setFile(file);
        Date date = new Date();
        newSecret.setDateUpdated(date);
        newSecret.setDateCreated(secret.getDateCreated());

        //Storing a new secret and deleting the old one
        secretInfoDAO.save(newSecret);
        secretInfoDAO.deleteById(secret.getSecretID());

        return ResponseEntity.ok("Secret " + name + " has been updated.");
    }
    /*
        Delete a secret
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSecret(@RequestParam UUID ID){
        //boolean status = true;
        try{
            SecretInfo secret = secretInfoDAO.getById(ID);
        }
        catch (NullPointerException nullPointerException){
            System.out.println("Error code 404: ");
            return ResponseEntity.badRequest().body("File does not exist");
        }
        SecretInfo secret = secretInfoDAO.getById(ID);
        String name = secret.getSecretName();
        secretInfoDAO.deleteById(ID);
        return ResponseEntity.ok("Secret " + name + " has been deleted.");
    }


}
