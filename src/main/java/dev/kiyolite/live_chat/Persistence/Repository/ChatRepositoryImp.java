/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.kiyolite.live_chat.Persistence.Repository;

import dev.kiyolite.live_chat.Entities.ChatWrapper;
import dev.kiyolite.live_chat.Entities.DB.Chat;
import dev.kiyolite.live_chat.Entities.DB.Message;
import dev.kiyolite.live_chat.Entities.MessageWrapper;
import dev.kiyolite.live_chat.Enums.MessageStatus;
import dev.kiyolite.live_chat.Persistence.DAO.MessageDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author soyky
 */
@Repository
public class ChatRepositoryImp implements ChatRepository {

    private SessionFactory factory;

    @Override
    public List<MessageWrapper> getMessagesFromChat(long chatId, int limit) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MessageWrapper> criteriaQuery = builder.createQuery(MessageWrapper.class);
        Root<Message> root = criteriaQuery.from(Message.class);
        Predicate filterByChatID = builder.equal(root.get("chat").get("id"), chatId);
        Order sortByCreationDate = builder.desc(root.get("creationDate"));
        Expression<String> creationDateAsString = builder.function(
                "DATE_FORMAT", String.class, root.get("creationDate"), builder.literal("%Y-%c-%d")
        );
        criteriaQuery.orderBy(sortByCreationDate);
        criteriaQuery.where(filterByChatID);
        criteriaQuery.multiselect(
                root.get("id"),
                root.get("content"),
                root.get("creator").get("id"),
                creationDateAsString
        );

        Query query = session.createQuery(criteriaQuery);
        query.setMaxResults(limit);
        List<MessageWrapper> messages = query.list();
        session.close();
        return messages;
    }

    @Override
    public List<MessageWrapper> getMessagesFromChat(long chatId, int limit, Calendar startDate) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<MessageWrapper> criteriaQuery = builder.createQuery(MessageWrapper.class);
        Root<Message> root = criteriaQuery.from(Message.class);
        Predicate filterByChatID = builder.equal(root.get("chat").get("id"), chatId);
        Predicate filterByPreviosCreationDate = builder.lessThan(root.get("creationDate"), startDate);
        Order sortByCreationDate = builder.desc(root.get("creationDate"));
        Expression<String> creationDateAsString = builder.function(
                "DATE_FORMAT", String.class, root.get("creationDate"), builder.literal("%Y-%c-%d")
        );
        criteriaQuery.orderBy(sortByCreationDate);
        criteriaQuery.where(builder.and(filterByChatID, filterByPreviosCreationDate));
        criteriaQuery.multiselect(
                root.get("id"),
                root.get("content"),
                root.get("creator").get("id"),
                creationDateAsString
        );
        Query query = session.createQuery(criteriaQuery);
        query.setMaxResults(limit);
        List<MessageWrapper> messages = query.list();
        session.close();
        return messages;
    }

    @Override
    public List<ChatWrapper> getContacts(long userID) {
        Session session = factory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ChatWrapper> criteriaQuery = builder.createQuery(ChatWrapper.class);
        Root<Message> root = criteriaQuery.from(Message.class);
        Join<Message, Chat> messageChat = root.join("chat", JoinType.INNER);

        criteriaQuery.groupBy(root.get("chat"));
        Predicate filterUserChats = builder.or(builder.equal(messageChat.get("user1").get("id"), userID), builder.equal(messageChat.get("user2").get("id"), userID));
        Predicate filterUnreadChat = builder.not(builder.equal(root.get("creator").get("id"), userID));
        criteriaQuery.where(builder.and(filterUserChats, filterUnreadChat));
        Expression readMessageStatusCase = builder.selectCase()
                .when(builder.equal(root.get("status"), MessageStatus.UNREAD.name()), 1)
                .otherwise(0);
        Expression totalUnreadMessagePerChat = builder.sum(readMessageStatusCase);
        Expression nameContactsCase = builder.selectCase()
                .when(builder.not(builder.equal(messageChat.get("user1").get("id"), userID)), messageChat.get("user1").get("userName"))
                .otherwise(messageChat.get("user2").get("userName"));
        criteriaQuery.multiselect(
                root.get("chat").get("id"),
                totalUnreadMessagePerChat,
                nameContactsCase
        );

        Query query = session.createQuery(criteriaQuery);
        List<ChatWrapper> chats = query.list();
        session.close();
        return chats;
    }

    @Autowired
    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }
}
