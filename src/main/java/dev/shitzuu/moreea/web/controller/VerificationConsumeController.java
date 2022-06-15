package dev.shitzuu.moreea.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shitzuu.moreea.web.domain.Verification;
import dev.shitzuu.moreea.web.domain.VerificationState;
import dev.shitzuu.moreea.web.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VerificationConsumeController {

    private final VerificationService verificationService;

    public VerificationConsumeController(@Autowired VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PutMapping(value = "/api/v1_0_0/verification/consume", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> consumeVerification(@Autowired ObjectMapper objectMapper, @RequestBody VerificationConsumer consumer) {
        Optional<Verification> optionalVerification = verificationService.getVerification(consumer.snowflake());
        if (optionalVerification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Verification verification = optionalVerification.get();
        if (verification.getState() != VerificationState.PENDING) {
            return ResponseEntity.badRequest()
                    .body(objectMapper.createObjectNode()
                            .put("status", HttpStatus.BAD_REQUEST.value())
                            .put("message", "Requested verification is not in pending state, that is why you cannot consume it."));
        }

        if (verification.getCode().equals(consumer.code())) {
            verificationService.deleteVerification(verification.getIssuer());
            return ResponseEntity.ok()
                    .body(objectMapper.createObjectNode()
                            .put("status", HttpStatus.OK.value())
                            .put("message", "Verification has been consumed successfully."));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                .body(objectMapper.createObjectNode()
                        .put("status", HttpStatus.UNAUTHORIZED.value())
                        .put("message", "Verification code is not valid."));
    }

    /**
     * Simple record for verification's consumer.
     */
    public record VerificationConsumer(String snowflake, String code) {

    }
}
