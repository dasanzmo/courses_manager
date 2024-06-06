package com.courses.courses.infrastructure.helper;

import com.courses.courses.api.dto.request.LessonReq;
import com.courses.courses.api.dto.response.LessonResp;
import com.courses.courses.domain.entities.Lesson;

public class LessonHelper {

    public static LessonResp lessonToResp(Lesson lesson){
        return LessonResp.builder()
        .id(lesson.getId())
        .content(lesson.getContent())
        .lessonTitle(lesson.getLessonTitle())
        .build();
    }

    public static Lesson reqToLesson(LessonReq lessonReq){
        return Lesson.builder()
        .lessonTitle(lessonReq.getLessonTitle())
        .content(lessonReq.getContent())
        .build();
    }

}
