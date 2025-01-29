package org.gso.citations_api.endpoint;

import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import lombok.extern.slf4j.Slf4j;
import org.gso.citations_api.dto.CitationDto;
import org.gso.citations_api.service.CitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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

    public CitationController(CitationService citationService) {
        this.citationService = citationService;
    }


    @Autowired
    private CitationService citationService;

    // Endpoint pour lire les citations validées
    @GetMapping("/random")
    public CitationDto getRandomValidatedCitation() {
        return citationService.getRandomValidatedCitation();
    }


    @PostMapping("/submit")
    public ResponseEntity<CitationDto> submitCitation(@RequestBody CitationDto citationDto, Authentication authentication) {
        log.info("User Authorities: {}", authentication.getAuthorities());

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("writer"))) {
            throw new AccessDeniedException("Forbidden");
        }

        CitationDto createdCitation = citationService.submitCitation(citationDto, authentication.getName());

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/api/v1/citations/{id}")
                                .buildAndExpand(createdCitation.getId())
                                .toUri()
                ).body(createdCitation);
    }



    @GetMapping("/pending")
    public ResponseEntity<List<CitationDto>> getPendingCitations(Authentication authentication) {
        log.info("User Authorities: {}", authentication.getAuthorities());

        // Vérification explicite du rôle moderator
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("moderator"))) {
            throw new AccessDeniedException("Forbidden");
        }

        List<CitationDto> pendingCitations = citationService.getPendingCitations();
        return ResponseEntity.ok(pendingCitations);
    }

    @PostMapping("/validate")
    public ResponseEntity<CitationDto> validateCitation(@RequestBody Map<String, String> requestBody, Authentication authentication) {
        log.info("User Authorities: {}", authentication.getAuthorities());

        // Vérification explicite du rôle
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("moderator"))) {
            throw new AccessDeniedException("Forbidden");
        }

        String id = requestBody.get("id");
        String validatedBy = authentication.getName();

        // Validation de la citation
        CitationDto validatedCitation = citationService.validateCitation(id, validatedBy);

        return ResponseEntity.ok(validatedCitation);
    }


}

