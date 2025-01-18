import { errorManager, closeConnection, openConnection, recipeMessage } from "../websocket/event-listener.js";
const websocketUrl = "";
const socketConnection = new WebSocket(websocketUrl);
socketConnection.addEventListener("close", closeConnection);
socketConnection.addEventListener("open", openConnection);
socketConnection.addEventListener("error", errorManager);
socketConnection.addEventListener("message", recipeMessage);
export { socketConnection };
