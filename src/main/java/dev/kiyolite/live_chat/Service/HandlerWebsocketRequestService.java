/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kiyolite.live_chat.Entities.WebsocketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 *
 * @author soyky
 */
@Service
public class HandlerWebsocketRequestService {
    
    private ObjectMapper objMapper;
    
    static long connectUser(WebsocketRequest request) {
        String payload = request.payload();
        return 0;
    }

    @Autowired
    public void setObjMapper(ObjectMapper objMapper) {
        this.objMapper = objMapper;
    }
    
    
    
    
    
}
