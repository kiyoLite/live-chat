import { apiCallContactsFromUser } from "../api/chat.js";
import { httpHanlder } from "../util/api-handler.js";
import { chatWrapper } from "../dto/chatWrapper.js";
import { chatId as attributeNameForChatId } from "../util/attributes-names.js";
import { getDomElementOrError } from "../util/dom-handler.js";
const builderConctactsView = async function (): Promise<HTMLDivElement[]> {
    const userName = sessionStorage.getItem("username");
    if (userName === null) return new Array();
    const contactsData: chatWrapper[] = await httpHanlder(await apiCallContactsFromUser(userName));
    if (contactsData === null) return new Array();
    const contacts: HTMLDivElement[] = new Array(contactsData.length);

    for (const singleContactData of contactsData) {
        const curContact = document.createElement("div");

        curContact.setAttribute(attributeNameForChatId, singleContactData.chatId.toString());

        const contactName = document.createElement("p");
        contactName.textContent = singleContactData.contactName;
        curContact.insertAdjacentElement("afterbegin", contactName);
        if (singleContactData.unreadMessages > 0) {
            const totalUnreadMessages = document.createElement("span");
            totalUnreadMessages.textContent = singleContactData.unreadMessages.toString();
            curContact.insertAdjacentElement("beforeend", totalUnreadMessages);
        }
    }
    return contacts;
}

const contactView = async function () {
    const contactsContainerQuery = "#contacts"
    const contactsContainer = getDomElementOrError(contactsContainerQuery);
    if (contactsContainer === null) return;
    const contacts = await builderConctactsView();
    for (const singleContact of contacts) {
        contactsContainer.insertAdjacentElement("beforeend", singleContact);
    }
}

export { contactView }
