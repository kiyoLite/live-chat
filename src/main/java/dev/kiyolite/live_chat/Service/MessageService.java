/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import dev.kiyolite.live_chat.Entities.DB.Message;
import dev.kiyolite.live_chat.Entities.MessageWrapper;
import dev.kiyolite.live_chat.Entities.RequestLoadingMessages;
import dev.kiyolite.live_chat.Enums.MessageStatus;
import dev.kiyolite.live_chat.Persistence.DAO.MessageDAO;
import dev.kiyolite.live_chat.Persistence.Repository.ChatRepository;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author soyky
 */
@Service
public class MessageService {

    private MessageDAO dao;
    private ChatRepository chatRepository;

    public void changeMessageStatusAsRead(List<Message> messages) {
        for (Message singleMessages : messages) {
            singleMessages.setStatus(MessageStatus.READ);
        }
        dao.saveAll(messages);
    }

    private List<MessageWrapper> loadingLastMessages(long chatId, int totalMessges) {
        return chatRepository.getMessagesFromChat(chatId, totalMessges);
    }
    
    public List<MessageWrapper> loadingMoreMessages(RequestLoadingMessages request) throws Exception {
        long chatId = request.chatId();
        String startDateAsString = request.startDate();
        int totalMessages = request.totalMessages();

        if (StringUtils.hasText(startDateAsString))return loadingLastMessages(chatId, totalMessages);
        
        SimpleDateFormat dateCaster = new SimpleDateFormat("yyyy-m-dd");
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(dateCaster.parse(startDateAsString));
        return chatRepository.getMessagesFromChat(chatId, totalMessages, startDate);

    }
    


    @Autowired
    public void setDAO(MessageDAO dao) {
        this.dao = dao;
    }
}
