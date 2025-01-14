const apiCallMessagesFromChat = function (chatId: number, starDate: string, totalMessages: number) {
    const url = "http://localhost:8080/api/message";
    const httpMethod = "GET";
    const bodyRequest = JSON.stringify({
        "chatId": chatId,
        "startDate": starDate,
        "totalMessages": totalMessages

    })
    return fetch(url, { method: httpMethod, body: bodyRequest });
}

export { apiCallMessagesFromChat }