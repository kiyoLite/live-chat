/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Entities.WebsocketRequest;
import dev.kiyolite.live_chat.Enums.WebsocketRequestType;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author soyky
 */
@Service
public class WebsocketService extends TextWebSocketHandler{
    private ConcurrentHashMap<WebSocketSession,Long> connectUsers = new ConcurrentHashMap<>();
    private HandlerWebsocketRequestService requestHandler; 
    private ObjectMapper ObjectMapper;
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        connectUsers.put(session, null);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        connectUsers.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        WebsocketRequest request = ObjectMapper.convertValue(message.getPayload(), WebsocketRequest.class);
        WebsocketRequestType requestType = WebsocketRequestType.valueOf(request.websocketRequestType());
        switch(requestType){
            case CONNECT:
                long userId = HandlerWebsocketRequestService.connectUser(request);
                connectUsers.put(session,userId);
                break;
        }
    }

    
    
    public void setObjectMapper(ObjectMapper ObjectMapper) {
        this.ObjectMapper = ObjectMapper;
    }

    
    
    @Autowired
    public void setRequestHandler(HandlerWebsocketRequestService requestHandler) {
        this.requestHandler = requestHandler;
    }
    
    

    
}
