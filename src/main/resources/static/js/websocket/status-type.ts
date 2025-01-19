enum WebsocketRequestType {
    CONNECT = "CONNECT",
    SEND_MESSAGE = "SEND_MESSAGE"
}

enum WebsocketResponseType {
    SUCCESSFUL_CONNECT,
    BAD_CONNECT,
    SUCCESSFUL_SEND,
    BAD_SEND
}

export { WebsocketRequestType }