import { WebsocketResponseType } from "../websocket/status-type.js";

interface websocketResponse {
    responseType: WebsocketResponseType
    payload: string
}

export { websocketResponse }