package com.example.myblog.role;

import jakarta.persistence.*;
import lombok.*;

/**
 * Common lookup table for role types.
 * Table users has no FK to this; store role string (e.g. ROLE_USER,ROLE_ADMIN)
 * and reference when needed.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Role code used in code & Spring Security, e.g. ROLE_ADMIN, ROLE_USER */
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    /** Display name, e.g. Administrator, User */
    @Column(nullable = false, length = 100)
    private String name;
}
