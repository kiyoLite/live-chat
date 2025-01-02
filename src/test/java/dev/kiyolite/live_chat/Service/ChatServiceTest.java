/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import dev.kiyolite.live_chat.Entities.ChatWrapper;
import dev.kiyolite.live_chat.Entities.ContactAdditionRequest;
import dev.kiyolite.live_chat.Entities.DB.Chat;
import dev.kiyolite.live_chat.Entities.DB.Credential;
import dev.kiyolite.live_chat.Entities.DB.Message;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Enums.MessageStatus;
import dev.kiyolite.live_chat.Enums.Rol;
import dev.kiyolite.live_chat.Persistence.DAO.ChatDAO;
import dev.kiyolite.live_chat.Persistence.DAO.CredentialDAO;
import dev.kiyolite.live_chat.Persistence.DAO.MessageDAO;
import dev.kiyolite.live_chat.Persistence.DAO.UserDAO;
import jakarta.security.auth.message.callback.PrivateKeyCallback;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
public class ChatServiceTest {

    @Autowired
    private ChatService service;

    @Autowired
    ChatDAO chatDAO;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    CredentialDAO credentialDAO;
    @Autowired
    UserDAO userDAO;
    private Credential credentialOne;
    private Credential credentialTwo;
    private User userOne;
    private User userTwo;
    private Chat chat;
    private List<Message> messages;

    @BeforeAll
    public void setUpTestEnviroment() {
        credentialOne = new Credential("testPasswordOne", Rol.User, true);
        credentialTwo = new Credential("testPasswordTwo", Rol.User, true);
        credentialDAO.save(credentialOne);
        credentialDAO.save(credentialTwo);

        userOne = new User("testEmailOne", credentialOne, "testNameOne");
        userTwo = new User("testEmailTwo", credentialTwo, "testNameTwo");
        userDAO.save(userOne);
        userDAO.save(userTwo);

        chat = new Chat(userOne, userTwo);
        chatDAO.save(chat);

        messages = new ArrayList<>();
    }

    @AfterAll
    public void tearDownTestEnviroment() {
        chatDAO.delete(chat);
        userDAO.deleteAll(List.of(userOne, userTwo));
        credentialDAO.deleteAll(List.of(credentialOne, credentialTwo));
    }

    @AfterEach
    public void tearDownTest() {
        messageDAO.deleteAll(messages);
    }

    @Test
    public void getAllContactsFromUserWioutContacts() {
        ResponseEntity<List<ChatWrapper>> response = service.getContactsFromUser(userOne);
        List<ChatWrapper> contacts = response.getBody();

        int expectTotalContacts = 0;
        int obtainTotalContacts = contacts.size();
        Assertions.assertEquals(expectTotalContacts, obtainTotalContacts);
    }

    @Test
    public void getAllContctsFromUser() {
        Message messageOne = new Message("hello this is a proof", Calendar.getInstance(), chat, userTwo, MessageStatus.READ);
        messages = List.of(messageOne);
        messageDAO.saveAll(messages);

        ResponseEntity<List<ChatWrapper>> response = service.getContactsFromUser(userOne);
        List<ChatWrapper> contacts = response.getBody();

        int expectTotalContacts = 1;
        int obtainTotalContacts = contacts.size();
        Assertions.assertEquals(expectTotalContacts, obtainTotalContacts);
        String expectContactName = userTwo.getUserName();
        String obtainContactName = contacts.get(0).contactName();
        Assertions.assertEquals(expectContactName, obtainContactName);
    }

    @Test
    public void getAllContactsByUserName() {
        Message messageOne = new Message("hello this is a proof", Calendar.getInstance(), chat, userTwo, MessageStatus.READ);
        messages = List.of(messageOne);
        messageDAO.saveAll(messages);

        String userNameQuery = userOne.getUserName();
        ResponseEntity<List<ChatWrapper>> response = service.getContactsFromUser(userNameQuery);
        List<ChatWrapper> contacts = response.getBody();

        boolean isSuccessResponse = response.getStatusCode().is2xxSuccessful();
        Assertions.assertTrue(isSuccessResponse);
        int expectTotalContacts = 1;
        int obtainTotalContacts = contacts.size();
        Assertions.assertEquals(expectTotalContacts, obtainTotalContacts);
        String expectContactName = userTwo.getUserName();
        String obtainContactName = contacts.get(0).contactName();
        Assertions.assertEquals(expectContactName, obtainContactName);
    }

    @Test
    public void getAllContactsByWrongUserName() {
        Message messageOne = new Message("hello this is a proof", Calendar.getInstance(), chat, userTwo, MessageStatus.READ);
        messages = List.of(messageOne);
        messageDAO.saveAll(messages);

        String userNameQuery = userOne.getUserName() + "Error";
        ResponseEntity<List<ChatWrapper>> response = service.getContactsFromUser(userNameQuery);

        int expectHtppStatusCode = 404;
        int obtainHttpStatusCode = response.getStatusCode().value();
        Assertions.assertEquals(expectHtppStatusCode, obtainHttpStatusCode);
    }
}
