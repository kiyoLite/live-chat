/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import dev.kiyolite.live_chat.Entities.DB.Credential;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Persistence.DAO.CredentialDAO;
import dev.kiyolite.live_chat.Persistence.DAO.UserDAO;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author soyky
 */

@Service
public class CustomeUserDetailsService implements UserDetailsService{

    UserDAO dao ;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> possibleCredential = dao.findByUserName(username);
        User credential  = possibleCredential.orElseThrow(() -> new UsernameNotFoundException("dont find user witih name: " + username));
        UserDetails userDetails = credential.getUserDetais();
        return userDetails;
    }

    @Autowired
    public void setDao(UserDAO dao) {
        this.dao = dao;
    }
    
    
    
}
