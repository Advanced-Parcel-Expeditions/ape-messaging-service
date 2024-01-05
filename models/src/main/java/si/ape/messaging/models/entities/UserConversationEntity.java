package si.ape.messaging.models.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  The UserConversationEntity class is used to map to the `user_conversation` table in the database. The table
 *  represents a many-to-many relationship between the `user` and `conversation` tables.
 */
@Entity
@Table(name = "user_conversation")
@NamedQueries(value = {
        @NamedQuery(name = "UserConversationEntity.getAll",
                query = "SELECT uc FROM UserConversationEntity uc"),
        @NamedQuery(name = "UserConversationEntity.findByUserId",
                query = "SELECT uc FROM UserConversationEntity uc WHERE uc.userId = :userId"),
        @NamedQuery(name = "UserConversationEntity.findByUserConversationCombinedId",
                query = "SELECT uc FROM UserConversationEntity uc WHERE uc.userId = :userId AND uc.conversationId = :conversationId")
})
@IdClass(UserConversationEntity.UserConversationId.class)
public class UserConversationEntity {

    /** The user's id. */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /** The conversation's id. */
    @Id
    @Column(name = "conversation_id")
    private Integer conversationId;

    /**
     * The UserConversationId class is used to map the composite primary key of the `user_conversation` table to a Java
     * object. It is used by the JPA (Java Persistence API) to map the database table to the Java object.
     */
    public static class UserConversationId implements Serializable {

        /** The user's id. */
        private Integer userId;

        /** The conversation's id. */
        private Integer conversationId;

        /**
         * Constructs a new UserConversationId object with the specified user and conversation IDs.
         *
         * @param userId         The user's id.
         * @param conversationId The conversation's id.
         */
        public UserConversationId(Integer userId, Integer conversationId) {
            this.userId = userId;
            this.conversationId = conversationId;
        }

        /**
         * Gets the user's id.
         *
         * @return The user's id.
         */
        public Integer getUserId() {
            return userId;
        }

        /**
         * Sets the user's id.
         *
         * @param userId The user's id.
         */
        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        /**
         * Gets the conversation's id.
         *
         * @return The conversation's id.
         */
        public Integer getConversationId() {
            return conversationId;
        }

        /**
         * Sets the conversation's id.
         *
         * @param conversationId The conversation's id.
         */
        public void setConversationId(Integer conversationId) {
            this.conversationId = conversationId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserConversationId that)) return false;
            return getUserId().equals(that.getUserId()) &&
                    getConversationId().equals(that.getConversationId());
        }

    }

    /**
     * Gets the user's id.
     *
     * @return The user's id.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the user's id.
     *
     * @param userId The user's id.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the conversation's id.
     *
     * @return The conversation's id.
     */
    public Integer getConversationId() {
        return conversationId;
    }

    /**
     * Sets the conversation's id.
     *
     * @param conversationId The conversation's id.
     */
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

}
