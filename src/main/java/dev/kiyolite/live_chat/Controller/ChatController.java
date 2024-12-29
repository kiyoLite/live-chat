/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Controller;

import dev.kiyolite.live_chat.Entities.ChatWrapper;
import dev.kiyolite.live_chat.Service.ChatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author soyky
 */
@RestController
public class ChatController {
    
    private ChatService ChatService;
    
    @GetMapping("/contact/")
    public ResponseEntity<List<ChatWrapper>> getContacts(String userNameRequest){
        return ChatService.getContactsFromUser(userNameRequest);
    }

    
    
    @Autowired
    public void setChatService(ChatService ChatService) {
        this.ChatService = ChatService;
    }
    
    
}
