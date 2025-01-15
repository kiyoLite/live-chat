"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.apiCallMessagesFromChat = void 0;
const apiCallMessagesFromChat = function (chatId, starDate, totalMessages) {
    const url = "http://localhost:8080/api/message";
    const httpMethod = "GET";
    const bodyRequest = JSON.stringify({
        "chatId": chatId,
        "startDate": starDate,
        "totalMessages": totalMessages
    });
    return fetch(url, { method: httpMethod, body: bodyRequest });
};
exports.apiCallMessagesFromChat = apiCallMessagesFromChat;
