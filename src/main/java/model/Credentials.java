package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID credential_id;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private String password_hash;
    private LocalDateTime created_at;


    @PrePersist
    public void prePersist(){
        created_at = LocalDateTime.now();
    }



}
