package com.fnb.usermanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user_credentials")
public class Credentials {

    @Id
    @GeneratedValue
    private UUID credential_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // foreign key column
    private User user;

    private String password_hash;
    private LocalDateTime created_at;

    @PrePersist
    public void prePersist() {
        created_at = LocalDateTime.now();
    }
}
