package com.aviralproject.TaskManager.dto;

import com.aviralproject.TaskManager.Domain.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTaskRequestdto {
    private String title;
    private String description;
    private TaskStatus status;

    @NotNull(message = "User id is required")
    private Long assignedToId;
}
