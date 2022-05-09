package com.uvic.venus.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="userinfo")
public class UserInfo {

    @Id
    private String username;
    private String firstname;
    private String lastname;


    public UserInfo(String username, String firstName, String lastName) {
        this.username = username;
        this.firstname = firstName;
        this.lastname = lastName;
    }

    public UserInfo() {

    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username;  }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

}
