package com.uvic.venus.controller;


import com.uvic.venus.model.SecretInfo;
import com.uvic.venus.model.CreateSecretRequest;
import com.uvic.venus.model.EditSecretRequest;
import com.uvic.venus.repository.SecretInfoDAO;
import com.uvic.venus.repository.UserInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;
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
    public ResponseEntity<?> createNewSecret(@RequestBody CreateSecretRequest createSecretRequest){
        SecretInfo secret = new SecretInfo(createSecretRequest.getName(), createSecretRequest.getText());

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
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestBody EditSecretRequest req){
        SecretInfo newSecret = new SecretInfo(req.getName(), req.getText());
        Date date = new Date();
        newSecret.setDateUpdated(date);
        newSecret.setDateCreated(req.getSecret().getDateCreated());

        //Storing a new secret and deleting the old one
        secretInfoDAO.save(newSecret);
        secretInfoDAO.deleteById(req.getSecret().getSecretID());

        return ResponseEntity.ok("Secret " + req.getName() + " has been updated.");
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
