import { WebsocketResponseType } from "./status-type.js";
import { websocketResponse } from "../dto/websocketResponse.js";
import { insertMessageAsLast } from "../message/insert-new-message.js";
import { messageWrapper } from "../dto/messageWrapper.js";
import { openSendMessageChannel } from "../message/events.js";

const handlerWebsocketResponse = function (event: MessageEvent) {
    let websocketResponse: websocketResponse;
    try {
        const response = event.data;
        websocketResponse = JSON.parse(response);
    }
    catch (exeception) {
        console.error("cast error")
        websocketResponse = {
            payload: "",
            responseType: WebsocketResponseType.BAD_CAST
        }
    }
    return (websocketResponse);
}

const handlerResponse = function (response: websocketResponse): void {
    const responseType: WebsocketResponseType = response.responseType;
    switch (responseType) {
        case WebsocketResponseType.SUCCESSFUL_SEND:
            const message: messageWrapper = JSON.parse(response.payload);
            return insertMessageAsLast(message);
        case WebsocketResponseType.BAD_SEND:
            return;
        case WebsocketResponseType.SUCCESSFUL_CONNECT:
            openSendMessageChannel();
            return;
        case WebsocketResponseType.BAD_CONNECT:
            return

    }

}


export { handlerWebsocketResponse as handlerRecipeMessage }