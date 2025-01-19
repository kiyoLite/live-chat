/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Config;

import dev.kiyolite.live_chat.Service.WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 *
 * @author soyky
 */
@EnableWebSocket
@Configuration
public class WebsocketConfig implements WebSocketConfigurer{
    
    private WebsocketService chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/join");
    }
    
    @Autowired
    public void setChatHandler(WebsocketService chatHandler){
        this.chatHandler = chatHandler;
    }
    
    
}
