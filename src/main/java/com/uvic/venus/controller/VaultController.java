package com.uvic.venus.controller;

import com.uvic.venus.model.UserInfo;
import com.uvic.venus.model.SecretInfo;
import com.uvic.venus.model.CreateSecretRequest;
import com.uvic.venus.model.UpdateSecretRequest;
import com.uvic.venus.repository.SecretInfoDAO;
import com.uvic.venus.repository.UserInfoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<?> listAllSecrets(@RequestParam String username){
        UserInfo user = userInfoDAO.getById(username);
        return ResponseEntity.ok(user.getSecrets());
    }
    /*
        create a new secret
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createNewSecret(@RequestBody CreateSecretRequest createSecretRequest){
        UserInfo owner = userInfoDAO.findById(createSecretRequest.getOwner()).get();
        Date now = new Date();
        SecretInfo secret = new SecretInfo(createSecretRequest.getName(), createSecretRequest.getText(), now);
        /*
            maybe print out the secret for users to confirm the correct info
         */
        secretInfoDAO.save(secret);
        owner.getSecrets().add(secret);
        userInfoDAO.save(owner);
        System.out.println(secretInfoDAO.findAll());
        System.out.println(userInfoDAO.findAll());
        return ResponseEntity.ok(secret);
    }

    /*
        update a secret
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateSecret(@RequestBody UpdateSecretRequest updateSecretRequest){
        Optional<SecretInfo> optionalSecret = secretInfoDAO.findById(updateSecretRequest.getUuid());
        if(optionalSecret.isPresent()) {
            SecretInfo secret = optionalSecret.get();
            Date now = new Date();
            secret.setDateUpdated(now);
            secret.setSecretName(updateSecretRequest.getName());
            secret.setContent(updateSecretRequest.getText());

            //Storing a new secret
            secretInfoDAO.save(secret);

            return ResponseEntity.ok("Secret " + secret.getSecretID() + " has been updated.");
        }
        return ResponseEntity.badRequest().body("Secret " + updateSecretRequest.getUuid() + " does not exist");
    }

    /*
        Delete a secret
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSecret(@RequestParam String ID, @RequestParam String username){
        try{
            System.out.println(ID);
            System.out.println(username);
            UUID uuid = UUID.fromString(ID);
            UserInfo user = userInfoDAO.getById(username);
            System.out.println(user);
            SecretInfo secret = secretInfoDAO.getById(uuid);
            String name = secret.getSecretName();
            secretInfoDAO.deleteById(uuid);
            return ResponseEntity.ok("Secret " + name + " has been deleted.");
        }
        catch (NullPointerException nullPointerException){
            System.out.println("Error code 404: ");
            return ResponseEntity.badRequest().body("File does not exist");
        }catch(Exception e){
            System.out.println("Error code 404: ");
            System.out.println(e);
            return ResponseEntity.badRequest().body(e);
        }
    }



}
