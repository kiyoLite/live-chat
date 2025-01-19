var WebsocketRequestType;
(function (WebsocketRequestType) {
    WebsocketRequestType["CONNECT"] = "CONNECT";
    WebsocketRequestType["SEND_MESSAGE"] = "SEND_MESSAGE";
})(WebsocketRequestType || (WebsocketRequestType = {}));
var WebsocketResponseType;
(function (WebsocketResponseType) {
    WebsocketResponseType[WebsocketResponseType["SUCCESSFUL_CONNECT"] = 0] = "SUCCESSFUL_CONNECT";
    WebsocketResponseType[WebsocketResponseType["BAD_CONNECT"] = 1] = "BAD_CONNECT";
    WebsocketResponseType[WebsocketResponseType["SUCCESSFUL_SEND"] = 2] = "SUCCESSFUL_SEND";
    WebsocketResponseType[WebsocketResponseType["BAD_SEND"] = 3] = "BAD_SEND";
})(WebsocketResponseType || (WebsocketResponseType = {}));
export { WebsocketRequestType };
