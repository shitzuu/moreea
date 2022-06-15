package dev.shitzuu.moreea.web.domain;

import javax.persistence.Embeddable;

@Embeddable
public class VerificationIssuer {

    private String username;
    private String discriminator;
    private String snowflake;

    /**
     * Gets the username of entity which requested the verification.
     *
     * @return username of entity
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of entity which requested the verification.
     *
     * @param username of entity
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the discriminator of entity which requested the verification.
     *
     * @return discriminator of entity
     */
    public String getDiscriminator() {
        return discriminator;
    }

    /**
     * Gets the discriminator of entity which requested the verification.
     *
     * @param discriminator of entity
     */
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    /**
     * Gets the snowflake of entity which requested the verification.
     *
     * @return snowflake of entity
     */
    public String getSnowflake() {
        return snowflake;
    }

    /**
     * Sets the snowflake of entity which requested the verification.
     *
     * @param snowflake of entity
     */
    public void setSnowflake(String snowflake) {
        this.snowflake = snowflake;
    }
}
