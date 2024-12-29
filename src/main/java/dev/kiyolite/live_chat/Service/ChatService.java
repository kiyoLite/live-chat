/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import dev.kiyolite.live_chat.Entities.ChatWrapper;
import dev.kiyolite.live_chat.Entities.ContactAdditionRequest;
import dev.kiyolite.live_chat.Entities.DB.Chat;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Persistence.DAO.ChatDAO;
import dev.kiyolite.live_chat.Persistence.DAO.UserDAO;
import dev.kiyolite.live_chat.Persistence.Repository.ChatRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author soyky
 */
@Service
public class ChatService {

    private ChatRepository chatRepository;
    private UserDAO userDAO;
    private ChatDAO chatDAO;

    public ResponseEntity<List<ChatWrapper>> getContactsFromUser(User user) {
        long userId = user.getId();
        List<ChatWrapper> contacts = chatRepository.getContacts(userId);
        ResponseEntity<List<ChatWrapper>> response = new ResponseEntity<>(contacts, HttpStatus.ACCEPTED);
        return response;
    }

    public ResponseEntity<List<ChatWrapper>> getContactsFromUser(String userName) {
        Optional<User> possibleUser = userDAO.findByUserName(userName);
        ResponseEntity<List<ChatWrapper>> response;
        response = possibleUser.isPresent()
                ? getContactsFromUser(possibleUser.get()) :
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return response;

    }

    public ResponseEntity<Void> addContact(ContactAdditionRequest contactRequest, long userIdCreatorRequest) {
        String contactName = contactRequest.contactName();
        User creatorRequest = userDAO.findById(userIdCreatorRequest).get();
        Optional<User> possibleContact = userDAO.findByUserName(contactName);
        User contact = possibleContact.orElseGet(() -> null);
        ResponseEntity<Void> response;

        if (contact == null) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return response;
        }

        Chat chat = new Chat(contact, creatorRequest);
        chatDAO.save(chat);
        response = new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        return response;
    }

    @Autowired
    public void setChatRepository(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setChatDAO(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }
    
    

}
