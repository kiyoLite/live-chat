/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Persistence.DAO;

import dev.kiyolite.live_chat.Entities.DB.Chat;
import dev.kiyolite.live_chat.Entities.DB.Credential;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Enums.Rol;
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
public class ChatDAOTest {

    @Autowired
    ChatDAO chatDAO;
    private Credential credentialOne;
    private Credential credentialTwo;
    private User userOne;
    private User userTwo;

    @BeforeEach
    public void setUpTest() {
        credentialOne = new Credential("testNameOne", "testPasswordOne", Rol.User, true);
        credentialTwo = new Credential("testNameTwo", "testPasswordTwo", Rol.User, true);
        userOne = new User("testEmailOne", credentialOne);
        userTwo = new User("testEmailTwo", credentialTwo);
    }
    
    @Test 
    public void save(){
        Chat chat = new Chat(userOne,userTwo);
        chatDAO.save(chat);
        
        long actualId = chat.getId();
        short notExpectId = 0;
        Assertions.assertNotEquals(notExpectId, actualId);
        
    }
}
