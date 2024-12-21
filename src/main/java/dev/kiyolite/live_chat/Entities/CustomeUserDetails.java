/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Entities;

import dev.kiyolite.live_chat.Enums.Rol;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author soyky
 */
public class CustomeUserDetails implements UserDetails{
    
    private String userName;
    private String password;
    private List<GrantedAuthority> rols;
    private boolean isEnable;
    
    public CustomeUserDetails(){}

    public CustomeUserDetails(String userName, String password, Rol rol,boolean isEnable) {
        this.userName = userName;
        this.password = password;
        List<GrantedAuthority> rols = new ArrayList<>();
        rols.add(new SimpleGrantedAuthority(rol.name()));
        this.rols = rols;
        this.isEnable = isEnable;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }
    
}
