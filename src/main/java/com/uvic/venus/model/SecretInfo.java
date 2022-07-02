package com.uvic.venus.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name="secretinfo")
public class SecretInfo {

        /* secret:
                -name
                -ID
                -content
                -DateCreated
                -Owner
         */
        @Id
        private String secretName;
        private int secretID;   //need to change type to something else (currently int)
        private String content; //need to change type!!!
        private Date created;
        private String owner;


        public SecretInfo(String secretName, int secretID, String content, Date created, String owner) {
            this.secretName = secretName;
            this.secretID = secretID;
            this.content = content;
            this.created = created;
            this.owner = owner;
        }

        public SecretInfo() {
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
                    ", Owner='" + owner + '\'' +
                    ", Date Created='" + created + '\'' +
                    '}';
        }


}
