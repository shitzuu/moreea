package dev.shitzuu.moreea.web.service;

import dev.shitzuu.moreea.web.domain.Verification;
import dev.shitzuu.moreea.web.domain.VerificationIssuer;
import dev.shitzuu.moreea.web.domain.VerificationState;
import dev.shitzuu.moreea.web.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class VerificationService {

    private static final int ALPHABET_BOUND_MINIMUM = 97;
    private static final int ALPHABET_BOUND_MAXIMUM = 122;

    private final VerificationRepository verificationRepository;
    private final int codeLength;
    private final int codeSeparatorInterval;

    @Autowired
    public VerificationService(VerificationRepository verificationRepository,
                               @Value("${moreea.code.length}") int codeLength,
                               @Value("${moreea.code.interval}") int codeSeparatorInterval) {
        this.verificationRepository = verificationRepository;
        this.codeLength = codeLength;
        this.codeSeparatorInterval = codeSeparatorInterval;
    }

    /**
     * Gets the verification for the specified issuer.
     *
     * @param snowflake the snowflake of the issuer
     *
     * @return the verification for the specified issuer
     */
    @Cacheable(value = "verifications", key = "#snowflake")
    public Optional<Verification> getVerification(String snowflake) {
        return verificationRepository.getVerificationByIssuerSnowflake(snowflake);
    }

    /**
     * Creates the verification for the specified issuer.
     *
     * @param issuer verification issuer
     */
    public void createVerification(VerificationIssuer issuer) {
        Verification verification = new Verification();
        verification.setCode(this.getPaddedGeneratedCode(codeLength, codeSeparatorInterval));
        verification.setIssuer(issuer);
        verification.setState(VerificationState.PENDING);
        this.saveVerification(verification);
    }

    /**
     * Deletes the verification for the specified issuer.
     *
     * @param issuer the issuer of the verification to delete
     */
    @CacheEvict(value = "verifications", key = "#issuer.getSnowflake()")
    public void deleteVerification(VerificationIssuer issuer) {
        Optional<Verification> optionalVerification = this.getVerification(issuer.getSnowflake());
        optionalVerification.ifPresent(verificationRepository::delete);
    }

    /**
     * Saves the verification for the specified issuer.
     *
     * @param verification the verification to save
     */
    @CacheEvict(value = "verifications", key = "#verification.getIssuer().getSnowflake()")
    public void saveVerification(Verification verification) {
        verificationRepository.save(verification);
    }

    /**
     * Gets the padded generated code for the verification.
     *
     * @param length length of the code
     * @param interval interval of separators
     *
     * @return padded generated code for the verification
     */
    public String getPaddedGeneratedCode(int length, int interval) {
        return this.getPaddedValue(this.getGeneratedCode(length), interval);
    }

    /**
     * Gets the generated code for the verification.
     *
     * @param length length of the code
     *
     * @return generated code for the verification
     */
    public String getGeneratedCode(int length) {
        return ThreadLocalRandom.current().ints(ALPHABET_BOUND_MINIMUM, ALPHABET_BOUND_MAXIMUM + 1)
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString()
            .toUpperCase(Locale.ROOT);
    }

    private String getPaddedValue(String original, int interval) {
        StringBuilder formatted = new StringBuilder();
        for (int index = 0; index < original.length(); index++) {
            if (index % interval == 0 && index > 0) {
                formatted.append("-");
            }
            formatted.append(original.charAt(index));
        }
        return formatted.toString();
    }
}
