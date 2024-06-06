package com.courses.courses.api.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityResp {

    private Long id;
    private String activityTitle;
    private String description;
    private LocalDate dueDate;

}
