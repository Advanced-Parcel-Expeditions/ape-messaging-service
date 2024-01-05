package si.ape.messaging.lib;

/**
 * The Role class represents a data-transfer object for the role entity.
 */
public class Role {

    /** The id of the role. */
    private Integer id;

    /** The name of the role. */
    private String roleName;

    /**
     * Gets the role's id.
     *
     * @return the role's id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the role's id.
     *
     * @param id the role's id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the role's name.
     *
     * @return the role's name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the role's name.
     *
     * @param roleName the role's name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
