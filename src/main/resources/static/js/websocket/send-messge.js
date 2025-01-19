import { WebsocketRequestType } from "./request-type.js";
const sendMessage = function (content, receiverUserId, chatId, websocketConnection) {
    const messageToSend = {
        "content": content,
        "receiverUserId": receiverUserId,
        "chatId": chatId,
    };
    const socketRequest = {
        payload: JSON.stringify(messageToSend),
        websocketRequestType: WebsocketRequestType.SEND_MESSAGE
    };
    websocketConnection.send(JSON.stringify(socketRequest));
};
export { sendMessage };
