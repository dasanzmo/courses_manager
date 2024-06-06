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
public class EnrollmentReq {

    @NotNull(message = "Id required")
    private Long studentId;
    @NotNull(message = "Id required")
    private Long courseId;
}
