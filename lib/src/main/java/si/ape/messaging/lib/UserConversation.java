package si.ape.messaging.lib;

/**
 * The UserConversation class represents a data-transfer object for the user_conversation entity.
 */
public class UserConversation {

    /** The id of the user. */
    private Integer userId;

    /** The id of the conversation. */
    private Integer conversationId;

    /**
     * Gets the user's id.
     *
     * @return the user's id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the user's id.
     *
     * @param userId the user's id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the conversation's id.
     *
     * @return the conversation's id
     */
    public Integer getConversationId() {
        return conversationId;
    }

    /**
     * Sets the conversation's id.
     *
     * @param conversationId the conversation's id
     */
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

}
