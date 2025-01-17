var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { apiCallContactsFromUser } from "../api/chat.js";
import { httpHanlder } from "../util/api-handler.js";
import { chatId as attributeNameForChatId } from "../util/attributes-names.js";
import { getDomElementOrError } from "../util/dom-handler.js";
const builderConctactsView = function () {
    return __awaiter(this, void 0, void 0, function* () {
        const userName = sessionStorage.getItem("username");
        if (userName === null)
            return new Array();
        const contactsData = yield httpHanlder(yield apiCallContactsFromUser(userName));
        if (contactsData === null)
            return new Array();
        const contacts = new Array(contactsData.length);
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
    });
};
const contactView = function () {
    return __awaiter(this, void 0, void 0, function* () {
        const contactsContainerQuery = "#contacts";
        const contactsContainer = getDomElementOrError(contactsContainerQuery);
        if (contactsContainer === null)
            return;
        const contacts = yield builderConctactsView();
        for (const singleContact of contacts) {
            contactsContainer.insertAdjacentElement("beforeend", singleContact);
        }
    });
};
export { contactView };
