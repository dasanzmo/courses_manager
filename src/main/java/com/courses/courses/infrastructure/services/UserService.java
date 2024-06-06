package com.courses.courses.infrastructure.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.courses.courses.api.dto.request.UserReq;
import com.courses.courses.api.dto.response.EnrollmentResp;
import com.courses.courses.api.dto.response.SubmissionResp;
import com.courses.courses.api.dto.response.UserResp;
import com.courses.courses.api.dto.response.UserRespWithCourses;
import com.courses.courses.api.dto.response.UserRespWithSubmissions;
import com.courses.courses.domain.entities.User;
import com.courses.courses.domain.repositories.EnrollmentRepository;
import com.courses.courses.domain.repositories.SubmissionRepository;
import com.courses.courses.domain.repositories.UserRepository;
import com.courses.courses.infrastructure.abstract_services.IUserService;
import com.courses.courses.infrastructure.helper.EnrollmentHelper;
import com.courses.courses.infrastructure.helper.SubmissionHelper;
import com.courses.courses.infrastructure.helper.UserHelper;
import com.courses.courses.utils.enums.SortType;
import com.courses.courses.utils.exception.BadRequestException;
import com.courses.courses.utils.message.ErrorMessage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EnrollmentRepository enrollmentRepository;
    @Autowired
    private final SubmissionRepository submissionRepository;

    @Override
    public Page<UserResp> findAll(int page, int size, SortType sortType) {

        if (page < 0)
            page = 0;

        PageRequest pageRequest = null;

        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case ASC -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
            default -> throw new IllegalArgumentException("No valid sort: " + sortType);
        }

        Pageable pageable = pageRequest;
        return userRepository.findAll(pageable).map(user -> UserHelper.userToResp(user));

    }

    @Override
    public UserResp findByIdWithDetails(Long id) {
        return UserHelper.userToResp(findById(id));
    }

    @Override
    public UserResp create(UserReq request) {
        return UserHelper.userToResp(userRepository.save(UserHelper.reqToUser(request)));
    }

    @Override
    public UserResp update(UserReq request, Long id) {
        findById(id);
        User user = UserHelper.reqToUser(request);
        user.setId(id);
        return UserHelper.userToResp(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("user")));
    }

    @Override
    public UserRespWithCourses getUsersWithCourses(Long id) {
        List<EnrollmentResp> enrollments = enrollmentRepository.findByStudentId(id).stream()
                .map(enrollment -> EnrollmentHelper.enrollmentToResp(enrollment)).toList();
        User student = findById(id);
        return UserRespWithCourses.builder()
                .id(student.getId())
                .userName(student.getUserName())
                .email(student.getEmail())
                .fullname(student.getFullName())
                .roleUser(student.getRoleUser())
                .courses(enrollments)
                .build();

    }

    @Override
    public UserRespWithSubmissions getUserWithSubmissions(Long id) {
        List<SubmissionResp> submissions = submissionRepository.findByUserId(id).stream()
                .map(submission -> SubmissionHelper.submissionToResp(submission)).toList();
        User user = findById(id);
        return UserRespWithSubmissions.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .fullname(user.getFullName())
                .roleUser(user.getRoleUser())
                .submissions(submissions)
                .build();
    }

}
