package com.aviralproject.TaskManager.entity;


import com.aviralproject.TaskManager.Domain.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User assignedTo;

}
