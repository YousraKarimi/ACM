package org.gso.citations_api.repository;

import org.gso.citations_api.model.Citation;
import org.gso.citations_api.model.CitationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CitationRepository extends MongoRepository<Citation, String> {
    List<Citation> findByStatus(CitationStatus status);
}
