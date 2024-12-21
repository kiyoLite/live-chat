/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Entities.DB;

import dev.kiyolite.live_chat.Entities.CustomeUserDetails;
import dev.kiyolite.live_chat.Enums.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private boolean isEnable;
    
    public Credential(){}

    public Credential(String userName, String password, Rol rol, boolean isEnable) {
        this.userName = userName;
        this.password = password;
        this.rol = rol;
        this.isEnable = isEnable;
    }
    
    public CustomeUserDetails getCustomeUserDetals(){
        CustomeUserDetails customeUserDetails = new CustomeUserDetails(
                userName,
                password,
                rol,
                isEnable
        );
        
        return customeUserDetails;
    }
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }    
}
