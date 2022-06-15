package dev.shitzuu.moreea.web.controller.view;

import dev.shitzuu.moreea.web.domain.Verification;
import dev.shitzuu.moreea.web.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(@Autowired VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/verify/{snowflake}")
    public ModelAndView verify(@PathVariable String snowflake) {
        Optional<Verification> optionalVerification = verificationService.getVerification(snowflake);
        return new ModelAndView(optionalVerification.isPresent() ? "verification" : "verification404",
            optionalVerification
                .map(verification -> Map.of("verification", verification))
                .orElse(Collections.emptyMap()));
    }
}
