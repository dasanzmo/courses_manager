package com.courses.courses.infrastructure.helper;

import com.courses.courses.api.dto.request.SubmissionReq;
import com.courses.courses.api.dto.response.SubmissionResp;
import com.courses.courses.domain.entities.Submission;

public class SubmissionHelper {

    public static SubmissionResp submissionToResp(Submission submission){
        return SubmissionResp.builder()
        .id(submission.getId())
        .content(submission.getContent())
        .submissionDate(submission.getSubmissionDate())
        .grade(submission.getGrade())
        .build();
    }

    public static Submission reqToSubmission(SubmissionReq submissionReq){
        return Submission.builder()
        .grade(submissionReq.getGrade())
        .content(submissionReq.getContent())
        .build();
    }
}
