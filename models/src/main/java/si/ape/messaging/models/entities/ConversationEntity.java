package si.ape.messaging.models.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The ConversationEntity class is used to map to the `conversation` table in the database. The table represents a
 * conversation between two or more users. The table contains the conversation's name and the time at which it was
 * created.
 */
@Entity
@Table(name = "conversation")
@NamedQueries(value = {
        @NamedQuery(name = "ConversationEntity.getAll",
                query = "SELECT c FROM ConversationEntity c")
})
public class ConversationEntity {

    /** The conversation's id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** The conversation's name. */
    @Column(name = "name")
    private String name;

    /** The time at which the conversation was created. */
    @Column(name = "created_at")
    private Timestamp createdAt;

    /**
     * Gets the conversation's id.
     *
     * @return The conversation's id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the conversation's id.
     *
     * @param id The conversation's id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the conversation's name.
     *
     * @return The conversation's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the conversation's name.
     *
     * @param name The conversation's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the time at which the conversation was created.
     *
     * @return The time at which the conversation was created.
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the time at which the conversation was created.
     *
     * @param createdAt The time at which the conversation was created.
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
