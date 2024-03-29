package com.uvic.venus.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.File;
import java.util.Date;
import java.util.UUID;

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
        private String secretName;
        @Id
        private UUID secretID;
        private String content;
        private Date created;
        private Date lastUpdated;
        private File file;

        public SecretInfo(String secretName, String content, Date created) {
            this.secretName = secretName;
            this.content = content;
            this.created = created;
            generateSecretID();
        }

        public SecretInfo() {
        }

        public void generateSecretID(){
            UUID ID = UUID.randomUUID();
            setSecretID(ID);
        }

        public String getSecretName() {
            return secretName;
        }

        public void setSecretName(String secretName) {
            this.secretName = secretName;
        }

        public UUID getSecretID() {
            return secretID;
        }

        public void setSecretID(UUID secretID) {
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

        public boolean fileExists(){
            if (getFile() != null)
                return true;
            else
                return false;
        }

        public Date getDateCreated(){
            return created;
        }

        public void setDateCreated(Date created){
            this.created = created;
        }

        public Date getDateUpdated(){
           return lastUpdated;
         }

        public void setDateUpdated(Date lastUpdated){
            this.lastUpdated = lastUpdated;
        }

        @Override
        public String toString() {
            return "SecretInfo{" +
                    "  Name='" + secretName + '\'' +
                    ", Date Created='" + created + '\'' +
                    '}';
        }


}
