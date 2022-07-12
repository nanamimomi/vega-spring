package com.uvic.venus.controller;

import com.uvic.venus.model.UserInfo;
import com.uvic.venus.model.SecretInfo;
import com.uvic.venus.model.CreateSecretRequest;
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
        System.out.println(userInfoDAO.findById(createSecretRequest.getOwner()));
        Optional<UserInfo> owners = userInfoDAO.findById(createSecretRequest.getOwner());
        Set<UserInfo> owner = new HashSet<UserInfo>();
        owner.add(owners.get());
        Date now = new Date();
        SecretInfo secret = new SecretInfo(createSecretRequest.getName(), createSecretRequest.getText(), now, owner);
        /*
            maybe print out the secret for users to confirm the correct info
         */
        secretInfoDAO.save(secret);
        System.out.println(secret);
        return ResponseEntity.ok(secret);
    }

    // /*
    //     update a secret
    //  */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ResponseEntity<?> updateSecret(SecretInfo secret, @RequestParam String name, @RequestParam String content){
        SecretInfo newSecret = new SecretInfo(name, content, secret.getDateCreated(), secret.getSecretOwner());
        Date date = new Date();
        newSecret.setDateUpdated(date);

        //Storing a new secret and deleting the old one
        secretInfoDAO.save(newSecret);
        secretInfoDAO.deleteById(secret.getSecretID());

        System.out.println(secret);

        return ResponseEntity.ok("Secret " + name + " has been updated.");
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
