package com.uvic.venus.controller;

import com.uvic.venus.model.UserInfo;
import com.uvic.venus.model.SecretInfo;
import com.uvic.venus.model.ViewAllSecretRequest;
import com.uvic.venus.model.CreateSecretRequest;
import com.uvic.venus.model.UpdateSecretRequest;
import com.uvic.venus.model.DeleteSecretRequest;
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
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public ResponseEntity<?> listAllSecrets(@RequestBody ViewAllSecretRequest viewAllSecretRequest){
        UserInfo user = userInfoDAO.getById(viewAllSecretRequest.getOwner());
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
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteSecret(@RequestBody DeleteSecretRequest deleteSecretRequest){
        try{
            UUID uuid = UUID.fromString(deleteSecretRequest.getID());
            UserInfo user = userInfoDAO.getById(deleteSecretRequest.getOwner());
            SecretInfo secret = secretInfoDAO.getById(uuid);
            String name = secret.getSecretName();
            user.getSecrets().remove(secret);
            secretInfoDAO.deleteById(uuid);
            return ResponseEntity.ok("Secret " + name + " has been deleted.");
        }
        catch (NullPointerException nullPointerException){
            return ResponseEntity.badRequest().body("File does not exist");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }



}
