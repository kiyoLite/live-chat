import { socketConnection } from "../main/chat.js";
import { WebsocketRequestType } from "./status-type.js";
import { handlerRecipeMessage } from "./recipe-message-handler.js";

const setConnection = function (websocketConnection: WebSocket) {
    const authToken = sessionStorage.getItem("authToken");
    if (authToken === null || authToken === "") return stablishConnectionError();
    const connectionRequest = {
        jwtToken: authToken
    };
    const socketRequest = {
        payload: JSON.stringify(connectionRequest),
        websocketRequestType: WebsocketRequestType.CONNECT
    };
    websocketConnection.send(JSON.stringify(socketRequest));
};

const stablishConnectionError = function () {

}

const openConnection = function () {
    setConnection(socketConnection);
}

const closeConnectionError = function () {
}

const closeConnection = function (event: CloseEvent) {
    if (!event.wasClean)
        return closeConnectionError();
}


const recipeMessage = function (event: MessageEvent) {
    handlerRecipeMessage(event);
}

const errorManager = function (event: Event) {
    console.error('websocket had a error');
}



export { errorManager, recipeMessage, closeConnection, openConnection }