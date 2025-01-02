/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import dev.kiyolite.live_chat.Entities.DB.Message;
import dev.kiyolite.live_chat.Enums.MessageStatus;
import dev.kiyolite.live_chat.Persistence.DAO.MessageDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author soyky
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @Autowired
    MessageDAO messageDAO;

    @Test
    public void changeMessagesStatusAsRead() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("test content One", Calendar.getInstance(), null, null, MessageStatus.UNREAD));
        messages.add(new Message("test content Two", Calendar.getInstance(), null, null, MessageStatus.UNREAD));
        messages.add(new Message("test content Three", Calendar.getInstance(), null, null, MessageStatus.UNREAD));

        ResponseEntity response = messageService.changeMessageStatusAsRead(messages);
        List<Long> messagesID = messages.stream().map(curMessage -> curMessage.getId()).toList();
        List<Message> messagesMarkAsRead = messageDAO.findAllById(messagesID);

        boolean isSuccessfulResponse = response.getStatusCode().is2xxSuccessful();
        Assertions.assertTrue(isSuccessfulResponse);
        boolean areAllMessagesMarkAsRead = messagesMarkAsRead.stream().allMatch(curMessage -> curMessage.getStatus() == MessageStatus.READ);
        Assertions.assertTrue(areAllMessagesMarkAsRead);

    }
}
