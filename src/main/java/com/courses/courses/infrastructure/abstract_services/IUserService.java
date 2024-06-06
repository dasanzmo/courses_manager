package com.courses.courses.infrastructure.abstract_services;

import com.courses.courses.api.dto.request.UserReq;
import com.courses.courses.api.dto.response.UserResp;
import com.courses.courses.api.dto.response.UserRespWithCourses;
import com.courses.courses.api.dto.response.UserRespWithSubmissions;

public interface IUserService extends CrudService<UserReq,UserResp,Long>{

    public final String FIELD_BY_SORT = "userName";
    public UserRespWithCourses getUsersWithCourses(Long id);
    public UserRespWithSubmissions getUserWithSubmissions(Long id);

}
