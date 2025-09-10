package com.aviralproject.TaskManager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="users")
@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String timezone;
    private boolean isActive = true; // default active
}