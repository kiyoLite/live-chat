/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kiyolite.live_chat.Entities.AuthLogin;
import dev.kiyolite.live_chat.Entities.DB.Message;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Entities.MessageWrapper;
import dev.kiyolite.live_chat.Entities.SendMessageRequest;
import dev.kiyolite.live_chat.Entities.WebsocketRequest;
import dev.kiyolite.live_chat.Entities.WebsocketResponse;
import dev.kiyolite.live_chat.Enums.WebsocketResponseType;
import dev.kiyolite.live_chat.Persistence.DAO.UserDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    private ObjectMapper objMapper;
    private JWTService jwtService;
    private UserDAO userDAO;
    private MessageService messageService;
    private SimpleDateFormat dateCaster = new SimpleDateFormat("yyyy-MM-dd-H-m-s");

    public void tryConnectUser(WebsocketRequest request, WebSocketSession session) throws IOException {
        AuthLogin authLogin = objMapper.readValue(request.payload(), AuthLogin.class);
        String token = authLogin.jwtToken();
        String userName = jwtService.getSubject(token);
        Optional<User> possibleUser = userDAO.findByUserName(userName);
        User user = possibleUser.orElseGet(() -> null);

        if (!isPossibleConnectUser(user, token)) {
            closeConnection(session);
            return;
        }

        long userId = user.getId();
        startConnection(session, userId);
    }

    public void trySendMessage(WebsocketRequest request, WebSocketSession session) throws Exception {
        if (!isUserConnect(session)) {
            sendUserConnectionRequest(session);
            return;
        }
        String payload = request.payload();
        SendMessageRequest messageToSend = objMapper.readValue(payload, SendMessageRequest.class);
        sendMessage(session, messageToSend);
    }

    private void sendMessage(WebSocketSession session, SendMessageRequest messageToSend) throws Exception {
        ConcurrentHashMap<WebSocketSession, Long> connectUsers = WebsocketService.getConnectUsers();
        long userCreatorMessage = connectUsers.get(session);
        long receiverUserId = messageToSend.receiverUserId();
        boolean isReceiverconnect = connectUsers.containsValue(receiverUserId);
        if (isReceiverconnect) {
            Message saveMessage = messageService.saveMessage(messageToSend, true, userCreatorMessage);
            Set<Map.Entry<WebSocketSession, Long>> connections = connectUsers.entrySet();
            WebSocketSession receiverConnection = null;
            for (Map.Entry<WebSocketSession, Long> singleConnection : connections) {
                if (receiverUserId == singleConnection.getValue()) {
                    receiverConnection = singleConnection.getKey();
                    break;

                }
            }
            String now = dateCaster.format(new Date());
            MessageWrapper sendMessage = new MessageWrapper(saveMessage.getId(), messageToSend.content(), userCreatorMessage, now);
            WebsocketResponse websocketResponse = new WebsocketResponse(WebsocketResponseType.SUCCESSFUL_SEND, objMapper.writeValueAsString(sendMessage));
            TextMessage response = new TextMessage(objMapper.writeValueAsString(websocketResponse));
            receiverConnection.sendMessage(response);
            return;
        }
        messageService.saveMessage(messageToSend, false, userCreatorMessage);

    }

    private boolean isUserConnect(WebSocketSession session) {
        ConcurrentHashMap connectUsers = WebsocketService.getConnectUsers();
        boolean existSessionKey = connectUsers.containsKey(session);
        Long defaultLongValue = 0L;
        return existSessionKey && !connectUsers.get(session).equals(defaultLongValue);
    }

    private void sendUserConnectionRequest(WebSocketSession session) throws IOException {
        String connectionErrorMessage = "user doesn't have connection, connect before send message";
        WebsocketResponse websocketResponse = new WebsocketResponse(WebsocketResponseType.BAD_SEND, connectionErrorMessage);
        TextMessage response = new TextMessage(objMapper.writeValueAsString(websocketResponse));
        session.sendMessage(response);
    }

    private void startConnection(WebSocketSession session, long userId) throws JsonProcessingException, IOException {
        WebsocketService.getConnectUsers().put(session, userId);
        WebsocketResponse websocketResponse = new WebsocketResponse(WebsocketResponseType.SUCCESSFUL_CONNECT, null);
        TextMessage response = new TextMessage(objMapper.writeValueAsString(websocketResponse));
        session.sendMessage(response);
    }

    private boolean isPossibleConnectUser(User user, String token) {
        if (user == null || !jwtService.isValid(user.getUserDetais(), token)) {
            return false;
        }

        return true;
    }

    private void closeConnection(WebSocketSession session) throws IOException {
        String connectionErrorMessage = "session conection fail. session close";
        WebsocketResponse websocketResponse = new WebsocketResponse(WebsocketResponseType.BAD_CONNECT, connectionErrorMessage);
        TextMessage respone = new TextMessage(objMapper.writeValueAsString(websocketResponse));
        session.sendMessage(respone);
        session.close();
    }

    @Autowired
    public void setObjMapper(ObjectMapper objMapper) {
        this.objMapper = objMapper;
    }

    @Autowired
    public void setJwtService(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

}
