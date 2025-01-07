/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kiyolite.live_chat.Entities.AuthLogin;
import dev.kiyolite.live_chat.Entities.WebsocketRequest;
import dev.kiyolite.live_chat.Enums.WebsocketRequestType;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author soyky
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HandlerWebsocketRequestServiceTest {

    @Autowired
    WebsocketService WebsocketService;
    @Autowired
    ObjectMapper objMapper;
    private CompletableFuture<TextMessage> futureResponse;
    private WebSocketSession session;

    @BeforeEach
    public void setUpTest() throws InterruptedException, ExecutionException {
        futureResponse = new CompletableFuture<>();
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketSession clientSession = client
                .execute(
                        new TextWebSocketHandler() {
                    @Override
                    public void handleTextMessage(WebSocketSession session, TextMessage message) {
                        futureResponse.complete(message);
                    }
                },
                        "ws://localhost:8080/chat"
                )
                .get();
        this.session = clientSession;

    }

    @AfterEach
    public void tearDownTest() {
        futureResponse = null;
    }
    
    @Test
    public void NotConnect() throws JsonProcessingException, IOException, InterruptedException, ExecutionException {
        WebsocketService.getConnectUsers().put(session, 0L);
        String wrongAuthToken = "this.is.wrongToken"; 
        AuthLogin authUser = new AuthLogin(wrongAuthToken);
        WebsocketRequest websocketRequest = new WebsocketRequest(WebsocketRequestType.CONNECT.name(), objMapper.writeValueAsString(authUser));
        TextMessage request = new TextMessage(objMapper.writeValueAsString(websocketRequest));

        session.sendMessage(request);
        TextMessage response = futureResponse.get();
        
        String HopeconnectionErrorMessage = "session conection fail. session close";
        String obtainConnectionErrorMesage = response.getPayload();
        boolean areEqualsConnectionsErrorMessages = HopeconnectionErrorMessage.equals(obtainConnectionErrorMesage);
        Assertions.assertTrue(areEqualsConnectionsErrorMessages);
    }

}
