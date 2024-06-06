package com.courses.courses.infrastructure.abstract_services;

import com.courses.courses.api.dto.request.LessonReq;
import com.courses.courses.api.dto.response.LessonResp;
import com.courses.courses.api.dto.response.LessonRespWithActivities;

public interface ILessonService extends CrudService<LessonReq,LessonResp,Long>{

    public final String FIELD_BY_SORT = "lessonTitle";
    public LessonRespWithActivities getLessonWithActivities(Long id);
}
