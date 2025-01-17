var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { httpHanlder } from "../util/api-handler.js";
import { apiCallMessagesFromChat } from "../api/message.js";
import { getDomElementOrError } from "../util/dom-handler.js";
import { messageDate as atributteNameOfMesageDate, messageDate } from "../util/attributes-names.js";
const buildMessageViewFromWrapper = function (messageWrapper) {
    const regexTime = /(\b[01]?\d|2[0-3]):([0-5]?\d):([0-5]?\d)\b/;
    const regexDate = /\b(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])\b/;
    const messageView = document.createElement("div");
    const messageTime = document.createElement("time");
    messageTime.textContent = messageWrapper.creation_date.match(regexTime)[0];
    messageView.insertAdjacentElement("beforeend", messageTime);
    const messageContent = document.createElement("p");
    messageContent.textContent = messageWrapper.content;
    messageView.insertAdjacentElement("afterbegin", messageContent);
    const messsageDate = messageWrapper.creation_date.match(regexDate)[0];
    messageView.setAttribute(atributteNameOfMesageDate, messageDate);
    return messageView;
};
const builderPreviousMessages = function (chatId, starDate, totalMessages) {
    return __awaiter(this, void 0, void 0, function* () {
        const messagesData = yield httpHanlder(yield apiCallMessagesFromChat(chatId, starDate, totalMessages));
        if (messagesData === null)
            return new Array();
        const messages = new Array(messagesData.length);
        for (const singleMessageData of messagesData) {
            messages.push(buildMessageViewFromWrapper(singleMessageData));
        }
        return messages;
    });
};
const transformDateYYYYMMDDInPrettry = function (date) {
    const dateObject = new Date(date);
    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    const correspondingMonthFromDate = months[dateObject.getMonth()];
    const prettryDate = `${correspondingMonthFromDate} ${dateObject.getDate()}th ${dateObject.getFullYear()}`;
    return prettryDate;
};
const builderContainerMessagesSpecificDate = function (dateYYYYMMDD, prettyDate) {
    const container = document.createElement("div");
    container.classList.add("messages-specific-date");
    const dateTitle = document.createElement("h2");
    dateTitle.textContent = prettyDate;
    container.setAttribute(atributteNameOfMesageDate, dateYYYYMMDD);
    return container;
};
const previousMessageView = function (chatId, starDate, totalMessages) {
    return __awaiter(this, void 0, void 0, function* () {
        let OldestContainerMessages = getDomElementOrError(".messages-specific-date");
        const mainMessageContainer = getDomElementOrError("#messages-container");
        if (OldestContainerMessages === null)
            return;
        const messages = yield builderPreviousMessages(chatId, starDate, totalMessages);
        const regexDate = /\b(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])\b/;
        for (const curMessagse of messages) {
            const messageDate = curMessagse.getAttribute(atributteNameOfMesageDate);
            const isMessageFromContainer = messageDate === OldestContainerMessages.getAttribute(atributteNameOfMesageDate);
            if (!isMessageFromContainer) {
                OldestContainerMessages = builderContainerMessagesSpecificDate(messageDate, transformDateYYYYMMDDInPrettry(messageDate));
                mainMessageContainer.insertAdjacentElement("afterbegin", OldestContainerMessages);
            }
            OldestContainerMessages.firstElementChild.insertAdjacentElement("afterend", curMessagse);
        }
    });
};
const insertMessageWrapperAsLast = function (messageWrapper, lastContainer) {
    const mesageWiew = buildMessageViewFromWrapper(messageWrapper);
    const messageDate = mesageWiew.getAttribute(atributteNameOfMesageDate);
    const isContainerAndMessageHaveSameDate = messageDate === mesageWiew.getAttribute(atributteNameOfMesageDate);
    if (!isContainerAndMessageHaveSameDate) {
        lastContainer = builderContainerMessagesSpecificDate(messageDate, transformDateYYYYMMDDInPrettry(messageDate));
        const mainMessageContainer = getDomElementOrError("#messages-container");
        if (mainMessageContainer === null)
            return;
        mainMessageContainer.insertAdjacentElement("beforeend", lastContainer);
    }
    lastContainer.insertAdjacentElement("beforeend", mesageWiew);
};
export { previousMessageView };
