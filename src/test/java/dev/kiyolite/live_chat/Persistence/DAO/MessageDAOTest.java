/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Persistence.DAO;

import dev.kiyolite.live_chat.Entities.DB.Chat;
import dev.kiyolite.live_chat.Entities.DB.Credential;
import dev.kiyolite.live_chat.Entities.DB.Message;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Enums.MessageStatus;
import dev.kiyolite.live_chat.Enums.Rol;
import dev.kiyolite.live_chat.Persistence.DAO.MessageDAO;
import java.util.Calendar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 *
 * @author soyky
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageDAOTest {

    @Autowired
    MessageDAO dao;
    @Autowired
    ChatDAO chatDAO;
    Message message;
    Chat chat;
    User userOne;
    User userTwo;
    Credential credentialOne;
    Credential credentialTwo;

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
    public void save() {
        message = new Message("hello this is a proof", Calendar.getInstance(), chat, userOne, MessageStatus.READ);
        long defaultIdValue = message.getId();
        dao.save(message);
        long idAfterSaveEntity = message.getId();
        Assertions.assertNotEquals(defaultIdValue, idAfterSaveEntity);
    }

    @Test
    public void getMessageFromChat() {

    }
}
