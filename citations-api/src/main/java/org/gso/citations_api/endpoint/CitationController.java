package org.gso.citations_api.endpoint;

import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.extern.slf4j.Slf4j;
import org.gso.citations_api.dto.CitationDto;
import org.gso.citations_api.service.CitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:8888")

@Slf4j
@RestController
@RequestMapping(
        value = CitationController.PATH,
        produces = MediaType.APPLICATION_JSON_VALUE
)

public class CitationController {
    public static final String PATH = "/api/v1/citations";
    public static int MAX_PAGE_SIZE = 200;
    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();

    public CitationController(CitationService citationService) {
        this.citationService = citationService;
    }


    @Autowired
    private CitationService citationService;

    // Endpoint pour lire une citation validée
    @GetMapping("/random")
    public CitationDto getRandomValidatedCitation() {
        return citationService.getRandomValidatedCitation();
    }

    // Endpoint pour soumettre une citation (rôle: writer)
    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('ROLE_writer')")
    public CitationDto submitCitation(@RequestBody CitationDto citationDto) {
        return citationService.submitCitation(citationDto, citationDto.getSubmittedBy());
    }

    // Endpoint pour récupérer les citations non validées (rôle: moderator)
    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('ROLE_moderator')")
    public List<CitationDto> getPendingCitations() {
        return citationService.getPendingCitations();
    }

    // Endpoint pour valider une citation (rôle: moderator)
    @PostMapping("/validate")
    @PreAuthorize("hasAuthority('ROLE_moderator')")
    public CitationDto validateCitation(@RequestBody Map<String, String> requestBody) {
        String id = requestBody.get("id");
        String validatedBy = requestBody.get("validatedBy");
        log.info("Request received: {}", requestBody);
        return citationService.validateCitation(id, validatedBy);
    }


}

