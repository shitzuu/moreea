package dev.shitzuu.moreea.web.controller;

import dev.shitzuu.moreea.web.domain.Verification;
import dev.shitzuu.moreea.web.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VerificationGetController {

    private final VerificationService verificationService;

    public VerificationGetController(@Autowired VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping(value = "/api/v1_0_0/verification/get/{snowflake}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Verification> getVerification(@PathVariable String snowflake) {
        Optional<Verification> optionalVerification = verificationService.getVerification(snowflake);
        if (optionalVerification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalVerification.get());
    }
}
