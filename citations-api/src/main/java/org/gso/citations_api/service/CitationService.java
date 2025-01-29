package org.gso.citations_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.constraints.NotEmpty;
import java.util.Random;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.gso.citations_api.dto.CitationDto;
import org.gso.citations_api.model.Citation;
import org.gso.citations_api.model.CitationStatus;
import org.gso.citations_api.repository.CitationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitationService {

    @Autowired
    private CitationRepository citationRepository;

    // Lire une citation validée aléatoire
    public CitationDto getRandomValidatedCitation() {
        List<Citation> validatedCitations = citationRepository.findByStatus(CitationStatus.VALIDATED);
        if (validatedCitations.isEmpty()) {
            throw new RuntimeException("Aucune citation validée disponible");
        }
        Random random = new Random();
        Citation randomCitation = validatedCitations.get(random.nextInt(validatedCitations.size()));
        return randomCitation.toDto();
    }

    // Soumettre une citation
    public CitationDto submitCitation(CitationDto citationDto, String submittedBy) {
        Citation citation = citationDto.toModel();
        citation.setSubmittedBy(submittedBy);
        citation.setSubmittedDate(LocalDateTime.now());
        citation.setStatus(CitationStatus.PENDING);
        citation = citationRepository.save(citation);
        return citation.toDto();
    }


    // Récupérer toutes les citations en attente
    public List<CitationDto> getPendingCitations() {
        List<Citation> pendingCitations = citationRepository.findByStatus(CitationStatus.PENDING);
        return pendingCitations.stream().map(Citation::toDto).toList();
    }

    // Valider une citation
    public CitationDto validateCitation(String citationId, String validatedBy) {
        Citation citation = citationRepository.findById(citationId)
                .orElseThrow(() -> new RuntimeException("Citation introuvable"));
        citation.setStatus(CitationStatus.VALIDATED);
        citation.setValidatedBy(validatedBy);
        citation.setValidatedDate(LocalDateTime.now());
        citation = citationRepository.save(citation);
        return citation.toDto();
    }
}
