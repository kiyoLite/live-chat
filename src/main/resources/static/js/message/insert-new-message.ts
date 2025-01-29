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


const transformDateYYYYMMDDInPrettry = function (date: string) {
    const dateObject = new Date(date);
    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    const correspondingMonthFromDate = months[dateObject.getMonth()];
    const prettryDate = `${correspondingMonthFromDate} ${dateObject.getDate()}th ${dateObject.getFullYear()}`;
    return prettryDate;
}

const builderContainerMessagesSpecificDate = function (dateYYYYMMDD: string) {
    const container = document.createElement("div");
    container.classList.add("messages-specific-date");
    const dateTitle = document.createElement("h2");
    const prettyDate = transformDateYYYYMMDDInPrettry(dateYYYYMMDD);
    dateTitle.textContent = prettyDate;
    container.setAttribute(atributteNameOfMesageDate, dateYYYYMMDD);
    return container;
}


const insertMessageAsLast = function (messageWrapper: messageWrapper) {
    const mainMessageContainer = getDomElementOrError("#messages-container")!
    if (mainMessageContainer === null) return;
    let lastContainer = mainMessageContainer.lastElementChild;
    const mesageWiew = buildMessageViewFromWrapper(messageWrapper);
    const messageDate = mesageWiew.getAttribute(atributteNameOfMesageDate)!;
    const isContainerAndMessageHaveSameDate = messageDate === lastContainer?.getAttribute(atributteNameOfMesageDate);
    if (!isContainerAndMessageHaveSameDate) {
        lastContainer = builderContainerMessagesSpecificDate(messageDate);
        mainMessageContainer.insertAdjacentElement("beforeend", lastContainer);
    }
    lastContainer!.insertAdjacentElement("beforeend", mesageWiew);

}

export { insertMessageAsLast }