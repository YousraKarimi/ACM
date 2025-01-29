package org.gso.citations_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.gso.citations_api.model.Citation;
import org.gso.citations_api.model.CitationStatus;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CitationDto {
    private String id;
    @NotEmpty(message = "Le texte de la citation est requis")
    private String text;
    @NotEmpty(message = "L'auteur de la citation est requis")
    private String author;
    private String submittedBy;
    private LocalDateTime submittedDate;
    private String validatedBy;
    private LocalDateTime validatedDate;
    private CitationStatus status;

    public Citation toModel() {
        return Citation.builder()
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

