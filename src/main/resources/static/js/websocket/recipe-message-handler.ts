import { WebsocketResponseType } from "./status-type.js";
import { websocketResponse } from "../dto/websocketResponse.js";
import { insertMessageWrapperAsLast } from "../view/messageView.js";
import { messageWrapper } from "../dto/messageWrapper.js";

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
            return insertMessageWrapperAsLast(message);
        case WebsocketResponseType.BAD_SEND:

    }

}


export { handlerWebsocketResponse as handlerRecipeMessage }