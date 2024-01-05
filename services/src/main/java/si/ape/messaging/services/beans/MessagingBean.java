package si.ape.messaging.services.beans;

import si.ape.messaging.lib.Conversation;
import si.ape.messaging.lib.Message;
import si.ape.messaging.models.converters.ConversationConverter;
import si.ape.messaging.models.converters.UserConverter;
import si.ape.messaging.models.entities.ConversationEntity;
import si.ape.messaging.models.entities.MessageEntity;
import si.ape.messaging.models.entities.UserConversationEntity;
import si.ape.messaging.models.entities.UserEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The MessagingBean class is a stateless session bean that provides methods for manipulating the database. It provides
 * methods for adding messages, getting messages, adding users to conversations, etc. and thus handles all the business
 * logic of the microservice.
 */
@ApplicationScoped
public class MessagingBean {

    /** The messaging bean's logger. */
    private Logger log = Logger.getLogger(MessagingBean.class.getName());

    /** The entity manager. */
    @Inject
    private EntityManager em;

    /**
     * Adds a message to the database, linking it to the specified conversation and sender.
     *
     * @param conversationId The ID of the conversation in which the message has been sent.
     * @param senderId       The ID of the user who sent the message.
     * @param sentAt         The time at which the message was sent.
     * @param content        The content of the message.
     * @return The ID of the message that was added, or null if the message could not be added.
     */
    public Integer addMessage(Integer conversationId, Integer senderId, Instant sentAt, String content) {
        ConversationEntity conversation = em.find(ConversationEntity.class, conversationId);
        UserEntity sender = em.find(UserEntity.class, senderId);

        try {
            beginTx();
            MessageEntity message = new MessageEntity();
            message.setConversation(conversation);
            message.setSender(sender);
            message.setSentAt(java.sql.Timestamp.from(sentAt));
            message.setContent(content);
            em.persist(message);
            commitTx();

            return message.getId();
        } catch (Exception e) {
            rollbackTx();
            return null;
        }
    }

    /**
     * Gets all the messages in the specified conversation.
     *
     * @param conversationId The ID of the conversation.
     * @return A list of messages in the conversation, or null if the operation failed.
     */
    public List<Message> getMessages(Integer conversationId) {
        try {
            ConversationEntity conversation = em.find(ConversationEntity.class, conversationId);
            TypedQuery<MessageEntity> query = em.createNamedQuery("MessageEntity.findByConversationId", MessageEntity.class);
            query.setParameter("conversationId", conversationId);
            List<MessageEntity> messages = query.getResultList();

            return messages.stream().map(message -> {

                Message dtoMessage = new Message();
                dtoMessage.setId(message.getId());
                dtoMessage.setConversation(ConversationConverter.toDto(conversation));
                dtoMessage.setSender(UserConverter.toDto(message.getSender()));
                dtoMessage.setSentAt(message.getSentAt());
                dtoMessage.setContent(message.getContent());
                return dtoMessage;

            }).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds a user to a conversation.
     *
     * @param conversationId The ID of the conversation.
     * @param userId         The ID of the user.
     * @return The ID of the conversation to which the user has been added, or null if the operation failed.
     */
    public Integer addUserToConversation(Integer conversationId, Integer userId) {
        try {
            beginTx();

            UserConversationEntity userConversation = new UserConversationEntity();
            userConversation.setConversationId(conversationId);
            userConversation.setUserId(userId);

            em.persist(userConversation);
            commitTx();

            return userConversation.getConversationId();
        } catch (Exception e) {
            rollbackTx();
            return null;
        }
    }

    /**
     * Removes a user from a conversation.
     *
     * @param conversationId The ID of the conversation.
     * @param userId         The ID of the user.
     * @return The ID of the user that was removed, or null if the operation failed.
     */
    public Integer removeUserFromConversation(Integer conversationId, Integer userId) {
        try {
            ConversationEntity conversation = em.find(ConversationEntity.class, conversationId);
            UserEntity user = em.find(UserEntity.class, userId);

            beginTx();

            // Use the named query.
            TypedQuery<UserConversationEntity> query = em.createNamedQuery("UserConversationEntity.findByUserConversationCombinedId", UserConversationEntity.class);
            query.setParameter("userId", userId);
            query.setParameter("conversationId", conversationId);
            UserConversationEntity userConversation = query.getSingleResult();

            em.remove(userConversation);
            commitTx();

            return userId;
        } catch (Exception e) {
            rollbackTx();
            return null;
        }
    }

    /**
     * Creates a conversation with the specified users.
     *
     * @param userIds The IDs of the users.
     * @return The ID of the conversation that was created, or null if the operation failed. The operation can fail
     *         either if one of the userIds is invalid, or if the conversation could not be created. There is currently
     *         no way to distinguish between these two cases.
     */
    public Integer createConversation(List<Integer> userIds) {
        try {
            beginTx();

            // First, construct the conversation entity. The conversation name is a comma-separated list of usernames.
            ConversationEntity conversation = new ConversationEntity();
            conversation.setCreatedAt(java.sql.Timestamp.from(Instant.now()));

            StringBuilder stringBuilder = new StringBuilder();
            for (Integer userId : userIds) {
                UserEntity user = em.find(UserEntity.class, userId);
                if (user == null) {
                    rollbackTx();
                    return null;
                } else {
                    stringBuilder.append(user.getUsername());
                    if (userIds.indexOf(userId) != userIds.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
            }
            conversation.setName(stringBuilder.toString());
            em.persist(conversation);
            commitTx();

            beginTx();

            Integer conversationId = conversation.getId();
            if (conversationId == null) {
                rollbackTx();
                return null;
            }

            // For each user, create an entry in the user_conversation table to link them to the conversation.
            for (Integer userId : userIds) {
                UserEntity user = em.find(UserEntity.class, userId);
                if (user == null) {
                    rollbackTx();
                    return null;
                }
                UserConversationEntity userConversation = new UserConversationEntity();
                userConversation.setConversationId(conversationId);
                userConversation.setUserId(userId);
                em.persist(userConversation);
            }
            commitTx();

            return conversation.getId();
        } catch (Exception e) {
            rollbackTx();
            return null;
        }
    }

    /**
     * Gets all the conversations in which the specified user is a participant.
     *
     * @param userId The ID of the user.
     * @return A list of conversations in which the user is a participant, or null if the operation failed.
     */
    public List<Conversation> getConversations(Integer userId) {
        try {
            UserEntity user = em.find(UserEntity.class, userId);

            TypedQuery<UserConversationEntity> query = em.createNamedQuery("UserConversationEntity.findByUserId", UserConversationEntity.class);
            query.setParameter("userId", userId);
            List<UserConversationEntity> userConversations = query.getResultList();

            return userConversations.stream().map(userConversation -> {

                Conversation dtoConversation = new Conversation();
                dtoConversation.setId(userConversation.getConversationId());
                return dtoConversation;

            }).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Begins a transaction if one is not already active.
     */
    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    /**
     * Commits a transaction if one is active.
     */
    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    /**
     * Rolls back a transaction if one is active.
     */
    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}
