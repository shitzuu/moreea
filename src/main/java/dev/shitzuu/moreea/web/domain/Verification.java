package dev.shitzuu.moreea.web.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String code;
    private VerificationIssuer issuer;
    private VerificationState state;

    /**
     * Gets the identifier of the verification entity.
     *
     * @return identifier of the verification
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the code for the verification.
     *
     * @return code for the verification
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code for the verification.
     *
     * @param code code for the verification
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the issuer of the verification.
     *
     * @return issuer of the verification
     */
    public VerificationIssuer getIssuer() {
        return issuer;
    }

    /**
     * Sets the issuer of the verification.
     *
     * @param issuer of the verification
     */
    public void setIssuer(VerificationIssuer issuer) {
        this.issuer = issuer;
    }

    /**
     * Gets the state of the verification.
     *
     * @return state of the verification
     */
    public VerificationState getState() {
        return state;
    }

    /**
     * Sets the state of the verification.
     *
     * @param state state of the verification
     */
    public void setState(VerificationState state) {
        this.state = state;
    }
}
