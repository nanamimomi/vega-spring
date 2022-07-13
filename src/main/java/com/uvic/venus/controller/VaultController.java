package com.uvic.venus.controller;

import com.uvic.venus.model.UserInfo;
import com.uvic.venus.model.SecretInfo;
import com.uvic.venus.model.CreateSecretRequest;
import com.uvic.venus.model.UpdateSecretRequest;
import com.uvic.venus.repository.SecretInfoDAO;
import com.uvic.venus.repository.UserInfoDAO;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Date;
import java.util.UUID;
import java.util.HashSet;

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
    public ResponseEntity<?> createNewSecret(@RequestBody CreateSecretRequest createSecretRequest){
        Optional<UserInfo> owners = userInfoDAO.findById(createSecretRequest.getOwner());
        Set<UserInfo> owner = new HashSet<UserInfo>();
        owner.add(owners.get());
        Date now = new Date();
        SecretInfo secret = new SecretInfo(createSecretRequest.getName(), createSecretRequest.getText(), now, owner);
        /*
            maybe print out the secret for users to confirm the correct info
         */
        secretInfoDAO.save(secret);
        System.out.println(secretInfoDAO.findAll());
        return ResponseEntity.ok(secret);
    }

    /*
        update a secret
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestBody UpdateSecretRequest updateSecretRequest){
        System.out.println(secretInfoDAO.findAll());
        Optional<SecretInfo> optionalSecret = secretInfoDAO.findById(updateSecretRequest.getUuid());
        if(optionalSecret.isPresent()) {
            System.out.println("=================UPDATE===================");
            SecretInfo secret = optionalSecret.get();
            Date now = new Date();
            secret.setDateUpdated(now);
            secret.setSecretName(updateSecretRequest.getName());
            secret.setContent(updateSecretRequest.getText());

            //Storing a new secret
            secretInfoDAO.save(secret);
            System.out.print(secret);

            return ResponseEntity.ok("Secret " + secret.getSecretID() + " has been updated.");
        }
        return ResponseEntity.badRequest().body("Secret " + updateSecretRequest.getUuid() + " does not exist");
    }

    /*
        Delete a secret
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSecret(@RequestParam String ID){
        UUID uuid = UUID.fromString(ID);
        try{
            SecretInfo secret = secretInfoDAO.getById(uuid);
            String name = secret.getSecretName();
            secretInfoDAO.deleteById(uuid);
            return ResponseEntity.ok("Secret " + name + " has been deleted.");
        }
        catch (NullPointerException nullPointerException){
            System.out.println("Error code 404: ");
            return ResponseEntity.badRequest().body("File does not exist");
        }
    }



}
