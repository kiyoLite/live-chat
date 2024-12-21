/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.kiyolite.live_chat.Persistence.Repository;

import dev.kiyolite.live_chat.Entities.ChatWrapper;
import dev.kiyolite.live_chat.Entities.MessageWrapper;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author soyky
 */

public interface ChatRepository {
    
    public List<MessageWrapper> getMessagesFromChat(long chatId , int limit);
    
    public List<MessageWrapper> getMessagesFromChat(long chatId , int limit, Calendar startDate);
    
    public List<ChatWrapper> getContacts(long userID);
    
}
