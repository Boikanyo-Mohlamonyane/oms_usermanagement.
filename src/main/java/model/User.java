package model;


import enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_credentials")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customer_id;
    private String first_name;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist() {
        created_at = LocalDateTime.now();

        if(role==null) role = Role.CUSTOMER;
    }

    @PreUpdate
    public void preUpdate() {
        updated_at = LocalDateTime.now();
    }

}
