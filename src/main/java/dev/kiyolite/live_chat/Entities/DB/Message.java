/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Entities.DB;

import dev.kiyolite.live_chat.Enums.MessageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Calendar;

/**
 *
 * @author soyky
 */
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String content;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation")
    Calendar creationDate;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    Chat chat;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;
    @Enumerated(EnumType.STRING)
    MessageStatus status;

    public Message() {
    }

    public Message(long id, String content, Calendar Date, Chat chat, User creator, MessageStatus status) {
        this.id = id;
        this.content = content;
        this.creationDate = Date;
        this.chat = chat;
        this.creator = creator;
        this.status = status;
    }

    public Message(String content, Calendar Date, Chat chat, User creator, MessageStatus status) {
        this.content = content;
        this.creationDate = Date;
        this.chat = chat;
        this.creator = creator;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getDate() {
        return creationDate;
    }

    public void setDate(Calendar Date) {
        this.creationDate = Date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

}
