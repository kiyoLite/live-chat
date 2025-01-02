/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import dev.kiyolite.live_chat.Entities.DB.Chat;
import dev.kiyolite.live_chat.Entities.DB.Credential;
import dev.kiyolite.live_chat.Entities.DB.Message;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Enums.Rol;
import dev.kiyolite.live_chat.Persistence.DAO.ChatDAO;
import dev.kiyolite.live_chat.Persistence.DAO.CredentialDAO;
import dev.kiyolite.live_chat.Persistence.DAO.MessageDAO;
import dev.kiyolite.live_chat.Persistence.DAO.UserDAO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

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

}
