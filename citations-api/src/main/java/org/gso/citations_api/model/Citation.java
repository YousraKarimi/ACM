package org.gso.citations_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.citations_api.dto.CitationDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.gso.citations_api.model.CitationStatus;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Citation {
    @Id
    private String id;
    private String text;
    private String author;
    private String submittedBy;
    private LocalDateTime submittedDate;
    private String validatedBy;
    private LocalDateTime validatedDate;
    private CitationStatus status;

    public CitationDto toDto() {
        return CitationDto.builder()
                .id(this.id)
                .text(this.text)
                .author(this.author)
                .submittedBy(this.submittedBy)
                .submittedDate(this.submittedDate)
                .validatedBy(this.validatedBy)
                .validatedDate(this.validatedDate)
                .status(this.status)
                .build();
    }

}