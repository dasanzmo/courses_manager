package com.courses.courses.api.dto.response;

import java.util.List;

import com.courses.courses.utils.enums.RoleUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRespWithSubmissions {

    private Long id;
    private String userName;
    private String email;
    private String fullname;
    private RoleUser roleUser;
    private List<SubmissionResp> submissions;
}
