package com.courses.courses.infrastructure.abstract_services;

import com.courses.courses.api.dto.request.CourseReq;
import com.courses.courses.api.dto.response.CourseResp;
import com.courses.courses.api.dto.response.CourseRespWithLessons;
import com.courses.courses.api.dto.response.CourseRespWithStudents;

public interface ICourseService extends CrudService<CourseReq,CourseResp,Long>{

    public final String FIELD_BY_SORT = "courseName";
    public CourseRespWithLessons getCourseWithLessons(Long id);
    public CourseRespWithStudents getCourseWithStudents(Long id);

}
