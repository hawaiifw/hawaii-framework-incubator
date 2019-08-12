package org.hawaiiframework.web.client;

/**
 * Basic Authorization data.
 */
public class BasicAuthorization {

    /**
     * The username.
     */
    private final String username;

    /**
     * The password.
     */
    private final String password;

    /**
     * Construct and {@link BasicAuthorization}.
     *
     * @param username the username.
     * @param password the password.
     */
    public BasicAuthorization(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets username.
     *
     * @return username value
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return password value
     */
    public String getPassword() {
        return password;
    }
}
