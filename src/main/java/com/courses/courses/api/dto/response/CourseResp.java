package com.courses.courses.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResp{

    private Long id;
    private String courseName;
    private String description;
    private UserResp instructor;

}
