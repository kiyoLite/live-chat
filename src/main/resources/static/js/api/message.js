const apiCallMessagesFromChat = function (chatId, starDate, totalMessages) {
    const url = "http://localhost:8080/api/message";
    const httpMethod = "POST";
    const bodyRequest = JSON.stringify({
        "chatId": chatId,
        "startDate": starDate,
        "totalMessages": totalMessages
    });
    return fetch(url, { method: httpMethod, headers: { "Content-type": "application/json" }, body: bodyRequest });
};
const apiCallMessageMarkAsRead = function (messages) {
    const url = "http://localhost:8080/api/message/status";
    const httpMethod = "PUT";
    const bodyRequest = JSON.stringify(messages);
    return fetch(url, { method: httpMethod, headers: { "Content-type": "application/json" }, body: bodyRequest });
};
export { apiCallMessagesFromChat, apiCallMessageMarkAsRead };
