/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Entities.DB;

import dev.kiyolite.live_chat.Entities.CustomeUserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author soyky
 */
@Entity
@Table(name = "member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(unique = true)
    String userName;
    String email;
    @OneToOne
    @JoinColumn(name = "credential_id")
    Credential credential;

    public User() {
    }

    public User(long id, String email, Credential credentials, String userName) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.credential = credentials;
    }

    public User(String email, Credential credentials, String userName) {
        this.email = email;
        this.credential = credentials;
        this.userName = userName;
    }
    
    public UserDetails getUserDetais(){
        CustomeUserDetails userDetails = new CustomeUserDetails(userName, credential.getPassword(), credential.getRol(), true);
        return userDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Credential getCredentials() {
        return credential;
    }

    public void setCredentials(Credential credentials) {
        this.credential = credentials;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
