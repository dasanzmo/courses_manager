package com.courses.courses.infrastructure.abstract_services;

import com.courses.courses.api.dto.request.EnrollmentReq;
import com.courses.courses.api.dto.response.EnrollmentResp;

public interface IEnrollmentService extends CrudService<EnrollmentReq,EnrollmentResp,Long>{

    public final String FIELD_BY_SORT = "date";

}
