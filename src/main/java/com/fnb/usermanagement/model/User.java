package com.fnb.usermanagement.model;

import com.fnb.usermanagement.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private UUID customer_id;

    private String first_name;
    private String surname;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 👇 Add this field and mapping
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Credentials credentials;
}
