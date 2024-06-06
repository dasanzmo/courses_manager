package com.courses.courses.infrastructure.helper;

import com.courses.courses.api.dto.request.CourseReq;
import com.courses.courses.api.dto.response.CourseResp;
import com.courses.courses.domain.entities.Course;

public class CourseHelper {

    public static CourseResp courseToResp(Course course) {
        return CourseResp.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .instructor(UserHelper.userToResp(course.getInstructor()))
                .build();
    }

    public static Course reqToCourse(CourseReq courseReq) {
        return Course.builder()
                .courseName(courseReq.getCourseName())
                .description(courseReq.getDescription())
                .build();
    }
}
