package com.subproblem.apikeysecurity.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String key;
    private LocalDateTime creationDate;
    private Boolean enabled;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", unique = true)
    private User user;
}
