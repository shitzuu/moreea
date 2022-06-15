package dev.shitzuu.moreea.web.repository;

import dev.shitzuu.moreea.web.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Integer> {

    /**
     * Gets the verification with matching snowflake of user who requested the verification.
     *
     * @param snowflake snowflake of user
     *
     * @return verification
     */
    Optional<Verification> getVerificationByIssuerSnowflake(String snowflake);
}
