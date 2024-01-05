package si.ape.messaging.models.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The MessageEntity class is used to map to the `message` table in the database. The table represents a message sent
 * by a user to a particular conversation. The table contains the message's content, the time at which it was sent,
 * the ID of the conversation to which it was sent and the sender's ID.
 */
@Entity
@Table(name = "message")
@NamedQueries(value = {
        @NamedQuery(name = "MessageEntity.findAll", query = "SELECT m FROM MessageEntity m"),
        @NamedQuery(name = "MessageEntity.findById", query = "SELECT m FROM MessageEntity m WHERE m.id = :id"),
        @NamedQuery(name = "MessageEntity.findByContent", query = "SELECT m FROM MessageEntity m WHERE m.content = :content"),
        @NamedQuery(name = "MessageEntity.findByConversationId", query = "SELECT m FROM MessageEntity m WHERE m.conversation.id = :conversationId"),
})
public class MessageEntity {

    /** The message's id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    /** The message's content. */
    @Column(name = "content")
    private String content;

    /** The time at which the message was sent. */
    @Column(name = "sent_at")
    private Timestamp sentAt;

    /** The conversation to which the message was sent. */
    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private ConversationEntity conversation;

    /** The user who sent the message. */
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private UserEntity sender;

    /**
     * Gets the message's id.
     *
     * @return The message's id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the message's id.
     *
     * @param id The message's id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the message's content.
     *
     * @return The message's content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the message's content.
     *
     * @param content The message's content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the time at which the message was sent.
     *
     * @return The time at which the message was sent.
     */
    public Timestamp getSentAt() {
        return sentAt;
    }

    /**
     * Sets the time at which the message was sent.
     *
     * @param sentAt The time at which the message was sent.
     */
    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    /**
     * Gets the conversation to which the message was sent.
     *
     * @return The conversation to which the message was sent.
     */
    public ConversationEntity getConversation() {
        return conversation;
    }

    /**
     * Sets the conversation to which the message was sent.
     *
     * @param conversation The conversation to which the message was sent.
     */
    public void setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
    }

    /**
     * Gets the user who sent the message.
     *
     * @return The user who sent the message.
     */
    public UserEntity getSender() {
        return sender;
    }

    /**
     * Sets the user who sent the message.
     *
     * @param sender The user who sent the message.
     */
    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

}
