package com.aviralproject.TaskManager.dto;

import com.aviralproject.TaskManager.Domain.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Taskdto {
    private long id;
    private String title;
    private String description;
    private TaskStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant updatedAt;

    private String assignedUserTimezone;

//
//    public Taskdto(long id, String title, String description, String status, String createdAt, String updatedAt) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.status = status;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }

}
