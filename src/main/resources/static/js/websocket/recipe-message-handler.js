import { WebsocketResponseType } from "./status-type.js";
import { insertMessageAsLast } from "../message/insert-new-message.js";
import { openSendMessageChannel } from "../message/events.js";
const handlerWebsocketResponse = function (event) {
    let websocketResponse;
    try {
        const response = event.data;
        websocketResponse = JSON.parse(response);
    }
    catch (exeception) {
        console.error("cast error");
        websocketResponse = {
            payload: "",
            responseType: WebsocketResponseType.BAD_CAST
        };
    }
    return (websocketResponse);
};
const handlerResponse = function (response) {
    const responseType = response.responseType;
    switch (responseType) {
        case WebsocketResponseType.SUCCESSFUL_SEND:
            const message = JSON.parse(response.payload);
            return insertMessageAsLast(message);
        case WebsocketResponseType.BAD_SEND:
            return;
        case WebsocketResponseType.SUCCESSFUL_CONNECT:
            openSendMessageChannel();
            return;
        case WebsocketResponseType.BAD_CONNECT:
            return;
    }
};
export { handlerWebsocketResponse as handlerRecipeMessage };
