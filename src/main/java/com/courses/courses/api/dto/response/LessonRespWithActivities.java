package com.courses.courses.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRespWithActivities {

    private Long id;
    private String lessonTitle;
    private String content;
    private List<ActivityResp> activities;

}
