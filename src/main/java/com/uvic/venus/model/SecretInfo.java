package com.uvic.venus.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="secretinfo")
public class SecretInfo {

        /* secret:
                -name
                -ID
                -content
                -Addition files
                -DateCreated
                -Owner
         */
        @Id
        private String secretName;
        private int secretID;   //need to change type to something else (currently int)
                                //secretID should be generated??
        private String content; //need to change type!!!
        private File file;
        private Date created;
        private String owner;


        public SecretInfo(String secretName, String content) {
            this.secretName = secretName;
            //this.secretID = secretID;
            this.content = content;
            //this.created = created;
            //this.owner = owner;
        }

        public SecretInfo() {
        }

        public void generateSecretID(){
            /*
                do something...
                getSecretID(id);
             */
        }

        public String getSecretName() {
            return secretName;
        }

        public void setSecretName(String secretName) {
            this.secretName = secretName;
        }

        public int getSecretID() {
            return secretID;
        }

        public void setSecretID(int secretID) {
            this.secretID = secretID;
        }

        public String getContent(){
            return this.content;
        }

        public void setContent(String content){
            this.content = content;
        }

        public File getFile(){
            return this.file;
        }

        public void setFile(File file){
            this.file = file;
        }

        public Date getDateCreated(){
            return created;
        }

        public void setDateCreated(Date created){
            this.created = created;
        }

        public String getSecretOwner(){
            return this.owner;
        }

        public void setSecretOwner(String owner) {
            this.owner = owner;
        }

        @Override
        public String toString() {
            return "SecretInfo{" +
                    "  Name='" + secretName + '\'' +
                    ", Date Created='" + created + '\'' +
                    ", Owner='" + owner + '\'' +
                    '}';
        }


}
