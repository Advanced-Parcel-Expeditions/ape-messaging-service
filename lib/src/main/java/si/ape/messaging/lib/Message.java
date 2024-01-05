package si.ape.messaging.lib;

import java.sql.Timestamp;

/**
 * The Message class represents a data-transfer object for the message entity.
 */
public class Message {

    /** The id of the message. */
    private Integer id;

    /** The content of the message. */
    private String content;

    /** The timestamp of the message. */
    private Timestamp sentAt;

    /** The conversation the message belongs to. */
    private Conversation conversation;

    /** The sender of the message. */
    private User sender;

    /**
     * Gets the message's id.
     *
     * @return the message's id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the message's id.
     *
     * @param id the message's id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the message's content.
     *
     * @return the message's content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the message's content.
     *
     * @param content the message's content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the message's timestamp.
     *
     * @return the message's timestamp
     */
    public Timestamp getSentAt() {
        return sentAt;
    }

    /**
     * Sets the message's timestamp.
     *
     * @param sentAt the message's timestamp
     */
    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    /**
     * Gets the message's conversation.
     *
     * @return the message's conversation
     */
    public Conversation getConversation() {
        return conversation;
    }

    /**
     * Sets the message's conversation.
     *
     * @param conversation the message's conversation
     */
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * Gets the message's sender.
     *
     * @return the message's sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * Sets the message's sender.
     *
     * @param sender the message's sender
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

}
