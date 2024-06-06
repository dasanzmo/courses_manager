package com.courses.courses.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionReq {

    private String content;
    @NotNull(message = "Grade required")
    private double grade;
    @NotNull(message = "User id required")
    private Long userId;
    @NotNull(message = "Activity id required")
    private Long activityId;
}
