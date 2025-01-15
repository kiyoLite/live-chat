/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Controller;

import dev.kiyolite.live_chat.Entities.MessageWrapper;
import dev.kiyolite.live_chat.Entities.RequestLoadingMessages;
import dev.kiyolite.live_chat.Service.MessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author soyky
 */
@RestController
@RequestMapping("/api")
public class MessageController {

    private MessageService messageService;

    @PostMapping("/message")
    public ResponseEntity<List<MessageWrapper>> getMessageFromChat(@RequestBody RequestLoadingMessages request) {
        return messageService.loadingMoreMessages(request);
    }

    @PutMapping("/message/status")
    public ResponseEntity<Void> markMessagesAsRead(List<MessageWrapper> messages) {
        return messageService.changeMessageStatusAsRead(messageService.clientToDBMessages(messages));
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

}
