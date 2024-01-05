package si.ape.messaging.lib;

/**
 * The User class represents a data-transfer object for the user entity.
 */
public class User {

    /** The id of the user. */
    private Integer id;

    /** The username of the user. */
    private String username;

    /** The password of the user. */
    private String password;

    /** The role of the user. */
    private Role role;

    /**
     * Gets the user's id.
     *
     * @return the user's id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the user's id.
     *
     * @param id the user's id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the user's username.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's role.
     *
     * @return the user's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     *
     * @param role the user's role
     */
    public void setRole(Role role) {
        this.role = role;
    }

}
