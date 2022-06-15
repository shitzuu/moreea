package dev.shitzuu.moreea.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shitzuu.moreea.web.domain.Verification;
import dev.shitzuu.moreea.web.domain.VerificationIssuer;
import dev.shitzuu.moreea.web.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VerificationCreateController {

    private final VerificationService verificationService;

    public VerificationCreateController(@Autowired VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping(value = "/api/v1_0_0/verification/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> createVerification(@Autowired ObjectMapper objectMapper, @RequestBody VerificationIssuer issuer) {
        Optional<Verification> optionalVerification = verificationService.getVerification(issuer.getSnowflake());
        if (optionalVerification.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(objectMapper.createObjectNode()
                            .put("status", HttpStatus.BAD_REQUEST.value())
                            .put("message", "Unfortunately, verification for that snowflake already exists, you cannot create next one, until the old one will be deleted."));
        }

        verificationService.createVerification(issuer);
        return ResponseEntity.ok()
                .body(objectMapper.createObjectNode()
                        .put("status", HttpStatus.OK.value())
                        .put("message", "Verification has been created successfully."));
    }
}
