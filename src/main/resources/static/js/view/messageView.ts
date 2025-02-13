import { httpHanlder } from "../util/api-handler.js"
import { apiCallMessagesFromChat } from "../api/message.js"
import { messageWrapper } from "../dto/messageWrapper.js";
import { getDomElementOrError } from "../util/dom-handler.js";
import { messageDate as atributteNameOfMesageDate, messageDate } from "../util/attributes-names.js";

const buildMessageViewFromWrapper = function (messageWrapper: messageWrapper) {
    const regexTime = /(\b[01]?\d|2[0-3]):([0-5]?\d):([0-5]?\d)\b/;
    const regexDate = /\b(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])\b/;
    const messageView = document.createElement("div");
    const messageTime = document.createElement("time");
    messageTime.textContent = messageWrapper.creation_date.match(regexTime)![0];
    messageView.insertAdjacentElement("beforeend", messageTime);
    const messageContent = document.createElement("p");
    messageContent.textContent = messageWrapper.content;
    messageView.insertAdjacentElement("afterbegin", messageContent);

    const messsageDate = messageWrapper.creation_date.match(regexDate)![0];
    messageView.setAttribute(atributteNameOfMesageDate, messageDate);
    return messageView;
}

const builderPreviousMessages = async function (chatId: number, starDate: string, totalMessages: number): Promise<HTMLDivElement[]> {
    const messagesData: messageWrapper[] = await httpHanlder(await apiCallMessagesFromChat(chatId, starDate, totalMessages));
    if (messagesData === null) return new Array();

    const messages: HTMLDivElement[] = new Array(messagesData.length);
    for (const singleMessageData of messagesData) {
        messages.push(buildMessageViewFromWrapper(singleMessageData));
    }
    return messages;


}

const transformDateYYYYMMDDInPrettry = function (date: string) {
    const dateObject = new Date(date);
    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    const correspondingMonthFromDate = months[dateObject.getMonth()];
    const prettryDate = `${correspondingMonthFromDate} ${dateObject.getDate()}th ${dateObject.getFullYear()}`;
    return prettryDate;
}


const builderContainerMessagesSpecificDate = function (dateYYYYMMDD: string, prettyDate: string) {
    const container = document.createElement("div");
    container.classList.add("messages-specific-date");
    const dateTitle = document.createElement("h2");
    dateTitle.textContent = prettyDate;
    container.setAttribute(atributteNameOfMesageDate, dateYYYYMMDD);
    return container;
}

const previousMessageView = async function (chatId: number, starDate: string, totalMessages: number) {
    let OldestContainerMessages = getDomElementOrError(".messages-specific-date")!;
    const mainMessageContainer = getDomElementOrError("#messages-container")!;

    if (OldestContainerMessages === null) return;

    const messages = await builderPreviousMessages(chatId, starDate, totalMessages);
    const regexDate = /\b(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])\b/;

    for (const curMessagse of messages) {
        const messageDate = curMessagse.getAttribute(atributteNameOfMesageDate)!;
        const isMessageFromContainer = messageDate === OldestContainerMessages.getAttribute(atributteNameOfMesageDate);
        if (!isMessageFromContainer) {
            OldestContainerMessages = builderContainerMessagesSpecificDate(messageDate, transformDateYYYYMMDDInPrettry(messageDate));
            mainMessageContainer.insertAdjacentElement("afterbegin", OldestContainerMessages);
        }
        OldestContainerMessages.firstElementChild!.insertAdjacentElement("afterend", curMessagse);
    }

}


const insertMessageWrapperAsLast = function (messageWrapper: messageWrapper) {
    const mainMessageContainer = getDomElementOrError("#messages-container")!
    if (mainMessageContainer === null) return;
    let lastContainer = mainMessageContainer.lastElementChild;
    const mesageWiew = buildMessageViewFromWrapper(messageWrapper);
    const messageDate = mesageWiew.getAttribute(atributteNameOfMesageDate)!;
    const isContainerAndMessageHaveSameDate = messageDate === lastContainer?.getAttribute(atributteNameOfMesageDate);
    if (!isContainerAndMessageHaveSameDate) {
        lastContainer = builderContainerMessagesSpecificDate(messageDate, transformDateYYYYMMDDInPrettry(messageDate));
        mainMessageContainer.insertAdjacentElement("beforeend", lastContainer);
    }
    lastContainer!.insertAdjacentElement("beforeend", mesageWiew);

}
export { previousMessageView, insertMessageWrapperAsLast }