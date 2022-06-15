package dev.shitzuu.moreea.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shitzuu.moreea.web.domain.Verification;
import dev.shitzuu.moreea.web.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VerificationDeleteController {

    private final VerificationService verificationService;

    public VerificationDeleteController(@Autowired VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @DeleteMapping(value = "/api/v1_0_0/verification/delete/{snowflake}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> deleteVerification(@Autowired ObjectMapper objectMapper, @PathVariable String snowflake) {
        Optional<Verification> optionalVerification = verificationService.getVerification(snowflake);
        if (optionalVerification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        verificationService.deleteVerification(optionalVerification.get().getIssuer());
        return ResponseEntity.ok()
                .body(objectMapper.createObjectNode()
                        .put("status", HttpStatus.OK.value())
                        .put("message", "Verification has been deleted successfully."));
    }
}
