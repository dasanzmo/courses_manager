package com.courses.courses.infrastructure.abstract_services;

import com.courses.courses.api.dto.request.SubmissionReq;
import com.courses.courses.api.dto.response.SubmissionResp;

public interface ISubmissionService extends CrudService<SubmissionReq,SubmissionResp,Long>{

    public final String FIELD_BY_SORT = "grade";

}
