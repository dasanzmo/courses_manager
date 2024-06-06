package com.courses.courses.api.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityRespWithSubmissions {

    private Long id;
    private String activityTitle;
    private String description;
    private LocalDate dueDate;
    private List<SubmissionResp> submissions;
}
