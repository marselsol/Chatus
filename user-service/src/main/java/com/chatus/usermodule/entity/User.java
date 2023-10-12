package com.chatus.usermodule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "com.chatus.usermodule.entity.User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(unique = true, nullable = false)
    private String email;
    @Transient
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String numberPhone;
    private LocalDateTime created;
    private LocalDateTime updated;

    @Builder
    public User(String login, String email, String password, String numberPhone, LocalDateTime created, LocalDateTime updated) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.numberPhone = numberPhone;
        this.created = created;
        this.updated = updated;
    }
}