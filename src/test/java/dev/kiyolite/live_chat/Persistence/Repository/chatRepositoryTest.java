/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Persistence.Repository;

import dev.kiyolite.live_chat.Entities.ChatWrapper;
import dev.kiyolite.live_chat.Entities.DB.Chat;
import dev.kiyolite.live_chat.Entities.DB.Credential;
import dev.kiyolite.live_chat.Entities.DB.Message;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Entities.MessageWrapper;
import dev.kiyolite.live_chat.Enums.MessageStatus;
import dev.kiyolite.live_chat.Enums.Rol;
import dev.kiyolite.live_chat.Persistence.DAO.ChatDAO;
import dev.kiyolite.live_chat.Persistence.DAO.MessageDAO;
import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author soyky
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class chatRepositoryTest {

    @Autowired
    ChatRepositoryImp repository;
    @Autowired
    ChatDAO chatDAO;
    @Autowired
    MessageDAO messageDAO;

    private Credential credentialOne;
    private Credential credentialTwo;
    private User userOne;
    private User userTwo;
    private Chat chat;

    @BeforeEach
    public void setUpTest() {
        credentialOne = new Credential("testNameOne", "testPasswordOne", Rol.User, true);
        credentialTwo = new Credential("testNameTwo", "testPasswordTwo", Rol.User, true);
        userOne = new User("testEmailOne", credentialOne);
        userTwo = new User("testEmailTwo", credentialTwo);
        chat = new Chat(userOne, userTwo);
        chatDAO.save(chat);
    }

    @Test
    public void getDefaultMessageFromChat() {
        Message messageOne = new Message("hello this is a proof", Calendar.getInstance(), chat, userOne, MessageStatus.READ);
        Message messageTwo = new Message("hello this is a proof two", Calendar.getInstance(), chat, userTwo, MessageStatus.READ);
        messageDAO.save(messageOne);
        messageDAO.save(messageTwo);

        long chatId = chat.getId();
        int quantityRegister = 2;
        List<MessageWrapper> messagesFromChat = repository.getMessagesFromChat(chatId, quantityRegister);
        int ExpectValue = 2;
        int obtainValue = messagesFromChat.size();
        Assertions.assertEquals(ExpectValue, obtainValue);

    }

    @Test
    public void getMessageFromChatBeforeCreationDate() {
        Calendar fiveMinutesAgo = Calendar.getInstance();
        fiveMinutesAgo.add(Calendar.MINUTE, -5);
        Calendar now = Calendar.getInstance();
        Message messageOne = new Message("hello this is a proof", fiveMinutesAgo, chat, userOne, MessageStatus.READ);
        Message messageTwo = new Message("hello this is a proof two", now, chat, userTwo, MessageStatus.READ);
        messageDAO.save(messageOne);
        messageDAO.save(messageTwo);

        long chatId = chat.getId();
        int dummyLimit = 100;
        List<MessageWrapper> messages = repository.getMessagesFromChat(chatId, dummyLimit, now);

        short expectTotalRegister = 1;
        int actualRegister = messages.size();
        Assertions.assertEquals(expectTotalRegister, actualRegister);

    }

//    @Test
    public void getContactsWithoutUnreadMessages() {
        long userId = userOne.getId();
        List<ChatWrapper> contacts = repository.getContacts(userId);

        short expectSize = 1;
        int actualSize = contacts.size();
        Assertions.assertEquals(expectSize, actualSize);

        short expectTotalUnreadMessages = 0;
        int ActualTotalUnreadMessages = contacts.get(0).unreadMessages();
        Assertions.assertEquals(expectTotalUnreadMessages, ActualTotalUnreadMessages);
    }

    @Test
    public void getContactsRecipeContactName() {
        Message messageOne = new Message("hello this is the first test message", Calendar.getInstance(), chat, userOne, MessageStatus.READ);
        messageDAO.save(messageOne);
        long userId = userOne.getId();
        List<ChatWrapper> contacts = repository.getContacts(userId);

        String expectContactName = userTwo.getCredentials().getUserName();
        String actualContactName = contacts.get(0).contactName();
        Assertions.assertEquals(expectContactName, actualContactName);

    }

    @Test
    public void getContacts() {
        long userId = userOne.getId();
        System.out.println(userId);
        List<ChatWrapper> contacts = repository.getContacts(userId);
        System.out.println(chat.getId());
        System.out.println(contacts);
    }
}
