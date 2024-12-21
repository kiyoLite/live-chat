/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import dev.kiyolite.live_chat.Entities.ChatWrapper;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Persistence.Repository.ChatRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author soyky
 */
@Service
public class ChatService {
    
    private ChatRepository chatRepository;
    
    public List<ChatWrapper> getContactsFromUser(User user){
        long userId = user.getId();
        List<ChatWrapper> contacts =chatRepository.getContacts(userId);
        return contacts;
    }
    
    
}
