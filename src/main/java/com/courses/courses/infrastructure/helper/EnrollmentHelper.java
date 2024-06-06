package com.courses.courses.infrastructure.helper;

import com.courses.courses.api.dto.response.EnrollmentResp;
import com.courses.courses.domain.entities.Enrollment;

public class EnrollmentHelper {

    public static EnrollmentResp enrollmentToResp(Enrollment enrollment){
        return EnrollmentResp.builder()
        .id(enrollment.getId())
        .date(enrollment.getDate())
        .course(CourseHelper.courseToResp(enrollment.getCourse()))
        .student(UserHelper.userToResp(enrollment.getStudent()))
        .build();
    }

    // public static Enrollment reqToLesson(EnrollmentReq enrollmentReq){
    //     return Enrollment.builder()
        
    //     .build();
    // }

}
