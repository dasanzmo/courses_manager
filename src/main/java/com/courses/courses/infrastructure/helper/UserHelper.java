package com.courses.courses.infrastructure.helper;

import com.courses.courses.api.dto.request.UserReq;
import com.courses.courses.api.dto.response.UserResp;
import com.courses.courses.domain.entities.User;

public class UserHelper {

    public static UserResp userToResp(User user) {
      return UserResp.builder()
          .id(user.getId())
          .userName(user.getUserName())
          .email(user.getEmail())
          .fullname(user.getFullName())
          .roleUser(user.getRoleUser())
          .build();
    }

    public static User reqToUser(UserReq userReq) {
        return User.builder()
            .userName(userReq.getUserName())
            .password(userReq.getPassword())
            .email(userReq.getEmail())
            .fullName(userReq.getFullName())
            .roleUser(userReq.getRoleUser())
            .build();
      }

}
