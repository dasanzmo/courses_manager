package com.courses.courses.api.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityReq {

    @NotBlank(message = "Activity title required.")
    private String activityTitle;
    private String description;
    @FutureOrPresent(message = "The date cannot be in the past.")
    private LocalDate dueDate;
    @NotNull(message = "Id required.")
    private Long lessonId;

}
