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
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

/**
 *
 * @author soyky
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/TestEmptyDB.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class MessageServiceTest {

    @Autowired
    MessageService messageService;
    @Autowired
    MessageDAO messageDAO;

    @Test
    @Sql("/testEntities.sql")
    @Sql("/TestMessagesUnread.sql")
    public void changeMessagesStatusAsRead() {
        List<Long> messagesID = List.of(1L, 2L, 3L);
        List<Message> messages = messageDAO.findAllById(messagesID);
        ResponseEntity response = messageService.changeMessageStatusAsRead(messages);
        List<Message> messagesMarkAsRead = messageDAO.findAllById(messagesID);

        boolean isSuccessfulResponse = response.getStatusCode().is2xxSuccessful();
        Assertions.assertTrue(isSuccessfulResponse);
        boolean areAllMessagesMarkAsRead = messagesMarkAsRead.stream().allMatch(curMessage -> curMessage.getStatus() == MessageStatus.READ);
        Assertions.assertTrue(areAllMessagesMarkAsRead);

    }

    @Test
    @Sql("/testEntities.sql")
    @Sql("/testMessagesFromToday.sql")
    public void getLastestMessages() {
        long chatIdFromSqlScript = 1;
        RequestLoadingMessages request = new RequestLoadingMessages(chatIdFromSqlScript, null, 2);
        messageService.loadingMoreMessages(request);
        ResponseEntity<List<MessageWrapper>> response = messageService.loadingMoreMessages(request);
        List<MessageWrapper> responseMessages = response.getBody();

        boolean isSuccessfulResponse = response.getStatusCode().is2xxSuccessful();
        Assertions.assertTrue(isSuccessfulResponse);
        int expectTotalMessages = 2;
        int obtainTotalMessages = responseMessages.size();
        Assertions.assertEquals(expectTotalMessages, obtainTotalMessages);
        System.out.println(responseMessages);
    }
}
