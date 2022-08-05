package com.uvic.venus.model;

import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Table;


@Entity
@Table(name="userinfo")
public class UserInfo {

    @Id
    private String username;
    private String firstname;
    private String lastname;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "owns_secrets",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "username") },
        inverseJoinColumns = { @JoinColumn(name = "secret_id", referencedColumnName = "") }
    )
    private Set<SecretInfo> secrets  = new HashSet<SecretInfo>();

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

    public Set<SecretInfo> getSecrets() { return secrets; }

    public void setSecrets(Set<SecretInfo> secrets) { this.secrets = secrets;  }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", secrets='" + secrets + '\'' +
                '}';
    }

}
