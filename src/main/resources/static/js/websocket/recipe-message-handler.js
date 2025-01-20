import { WebsocketResponseType } from "./status-type.js";
import { insertMessageWrapperAsLast } from "../view/messageView.js";
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
            return insertMessageWrapperAsLast(message);
        case WebsocketResponseType.BAD_SEND:
    }
};
export { handlerWebsocketResponse as handlerRecipeMessage };
