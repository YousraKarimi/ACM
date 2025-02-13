package org.gso.profiles.model;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gso.profiles.dto.ProfileDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ProfileModel {

    @Id
    private String id;
    private String userId;
    @Email
    private String mail;
    private int age;
    private String firstName;
    private String lastName;
    @CreatedDate
    private LocalDate created;
    @LastModifiedDate
    private LocalDate modified;

    public ProfileDto toDto() {
        return ProfileDto.builder()
                .id(this.id)
                .userId(this.userId)
                .mail(this.mail)
                .age(this.age)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .created(this.created)
                .modified(this.modified)
                .build();
    }
}
