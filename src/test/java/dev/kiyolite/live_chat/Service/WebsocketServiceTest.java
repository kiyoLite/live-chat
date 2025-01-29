/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kiyolite.live_chat.Entities.AuthLogin;
import dev.kiyolite.live_chat.Entities.DB.User;
import dev.kiyolite.live_chat.Entities.MessageWrapper;
import dev.kiyolite.live_chat.Entities.SendMessageRequest;
import dev.kiyolite.live_chat.Entities.WebsocketRequest;
import dev.kiyolite.live_chat.Entities.WebsocketResponse;
import dev.kiyolite.live_chat.Enums.WebsocketRequestType;
import dev.kiyolite.live_chat.Enums.WebsocketResponseType;
import dev.kiyolite.live_chat.Persistence.DAO.MessageDAO;
import dev.kiyolite.live_chat.Persistence.DAO.UserDAO;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
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
public class WebsocketServiceTest {

    @Autowired
    WebsocketService WebsocketService;

    @Autowired
    ObjectMapper objMapper;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    JWTService jwtService;
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
                        "ws://localhost:8080/join"
                )
                .get();
        this.session = clientSession;

    }

    @Test
    public void NotConnect() throws JsonProcessingException, IOException, InterruptedException, ExecutionException {
        WebsocketService.getConnectUsers().put(session, 0L);
        String wrongAuthToken = "this.is.wrongToken";
        AuthLogin authUser = new AuthLogin(wrongAuthToken);
        WebsocketRequest websocketRequest = new WebsocketRequest(WebsocketRequestType.CONNECT.name(), objMapper.writeValueAsString(authUser));
        TextMessage request = new TextMessage(objMapper.writeValueAsString(websocketRequest));

        session.sendMessage(request);
        WebsocketResponse response = objMapper.readValue(futureResponse.get().getPayload(), WebsocketResponse.class);

        Assertions.assertEquals(WebsocketResponseType.BAD_CONNECT, response.responseType());
    }

    @Sql("/TestWebsocketEnviroment.sql")
    @Sql(scripts = "/TestEmptyDB.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void connect() throws JsonProcessingException, IOException, InterruptedException, ExecutionException {
        long userIdFromScript = 1;
        User user = userDAO.findById(userIdFromScript).get();
        String authToken = jwtService.getToken(user.getUserDetais());
        AuthLogin authUser = new AuthLogin(authToken);
        WebsocketRequest websocketRequest = new WebsocketRequest(WebsocketRequestType.CONNECT.name(), objMapper.writeValueAsString(authUser));
        TextMessage request = new TextMessage(objMapper.writeValueAsString(websocketRequest));
        session.sendMessage(request);

        WebsocketResponse response = objMapper.readValue(futureResponse.get().getPayload(), WebsocketResponse.class);

        Assertions.assertEquals(WebsocketResponseType.SUCCESSFUL_CONNECT, response.responseType());
    }

    @Test
    @Sql("/TestWebsocketEnviroment.sql")
    @Sql(scripts = "/TestEmptyDB.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void trySendMessage() throws JsonProcessingException, Exception {
        connect();
        futureResponse = new CompletableFuture<>();
        long userIdOfChatFromSqlScript = 1;
        long chatIdFromSqlScript = 1;
        String messageContent = "test message";
        SendMessageRequest sendMessageRequest = new SendMessageRequest(messageContent, userIdOfChatFromSqlScript, chatIdFromSqlScript);
        WebsocketRequest websocketRequest = new WebsocketRequest(WebsocketRequestType.SEND_MESSAGE.name(), objMapper.writeValueAsString(sendMessageRequest));
        TextMessage request = new TextMessage(objMapper.writeValueAsString(websocketRequest));

        session.sendMessage(request);
        WebsocketResponse response = objMapper.readValue(futureResponse.get().getPayload(), WebsocketResponse.class);

        Assertions.assertEquals(WebsocketResponseType.SUCCESSFUL_SEND, response.responseType());

    }
}
