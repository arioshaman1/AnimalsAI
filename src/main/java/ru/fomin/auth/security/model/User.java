package ru.fomin.auth.security.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "user_table")
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name="user_name")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
