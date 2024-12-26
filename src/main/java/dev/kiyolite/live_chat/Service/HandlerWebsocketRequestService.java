/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kiyolite.live_chat.Entities.AuthLogin;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Entities.WebsocketRequest;
import dev.kiyolite.live_chat.Persistence.DAO.UserDAO;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author soyky
 */
@Service
public class HandlerWebsocketRequestService {

    private WebsocketService websocketService;
    private ObjectMapper objMapper;
    private JWTService jwtService;
    private UserDAO userDAO;

    public void connectUser(WebsocketRequest request, WebSocketSession session) throws IOException {
        AuthLogin authLogin = objMapper.convertValue(request.payload(), AuthLogin.class);
        String token = authLogin.jwtToken();
        String userName = jwtService.getSubject(token);
        Optional<User> possibleUser = userDAO.findByUserName(userName);
        User user = possibleUser.orElseGet(() -> null);

        if (isPossibleConnectUser(user, token)) {
            closeConnection(session);
            return;
        }

        long userId = user.getId();
        startConnection(session, userId);
    }
    
    public void sendMessage(WebsocketRequest request , WebSocketSession session){
        
    }

    private boolean isUserConnect(WebSocketSession session) {
        ConcurrentHashMap connectUsers = WebsocketService.getConnectUsers();
        boolean existSessionKey = connectUsers.containsKey(session);
        return existSessionKey && connectUsers.get(session) != null;
    }

    private void sendUserConnectionRequest(WebSocketSession session) throws  IOException{
        String connectionErrorMessage = "user doesn't have connection, connect before send message";
        session.sendMessage(new TextMessage(connectionErrorMessage));
    }
    
    

    private void startConnection(WebSocketSession session, long userId) {
        WebsocketService.getConnectUsers().put(session, userId);
    }

    private boolean isPossibleConnectUser(User user, String token) {
        if (user == null || !jwtService.isValidToken(user.getUserDetais(), token)) {
            return false;
        }

        return true;
    }

    private void closeConnection(WebSocketSession session) throws IOException {
        String connectionErrorMessage = "session conection fail. session close";
        session.sendMessage(new TextMessage(connectionErrorMessage));
        session.close();
    }

    @Autowired
    public void setObjMapper(ObjectMapper objMapper) {
        this.objMapper = objMapper;
    }

}
