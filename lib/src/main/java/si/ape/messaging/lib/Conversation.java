package si.ape.messaging.lib;

import java.sql.Timestamp;

/**
 * The Conversation class represents a data-transfer object for the conversation entity.
 */
public class Conversation {

    /** The id of the conversation. */
    private Integer id;

    /** The name of the conversation. */
    private String name;

    /** The timestamp of the conversation. */
    private Timestamp createdAt;

    /**
     * Gets the conversation's id.
     *
     * @return the conversation's id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the conversation's id.
     *
     * @param id the conversation's id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the conversation's name.
     *
     * @return the conversation's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the conversation's name.
     *
     * @param name the conversation's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the conversation's timestamp.
     *
     * @return the conversation's timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the conversation's timestamp.
     *
     * @param createdAt the conversation's timestamp
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
