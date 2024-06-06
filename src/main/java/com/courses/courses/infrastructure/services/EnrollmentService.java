package com.courses.courses.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.courses.courses.api.dto.request.EnrollmentReq;
import com.courses.courses.api.dto.response.EnrollmentResp;
import com.courses.courses.domain.entities.Course;
import com.courses.courses.domain.entities.Enrollment;
import com.courses.courses.domain.entities.User;
import com.courses.courses.domain.repositories.CourseRepository;
import com.courses.courses.domain.repositories.EnrollmentRepository;
import com.courses.courses.domain.repositories.UserRepository;
import com.courses.courses.infrastructure.abstract_services.IEnrollmentService;
import com.courses.courses.infrastructure.helper.EnrollmentHelper;
import com.courses.courses.utils.enums.SortType;
import com.courses.courses.utils.exception.BadRequestException;
import com.courses.courses.utils.message.ErrorMessage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnrollmentService implements IEnrollmentService {

    @Autowired
    private final EnrollmentRepository enrollmentRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CourseRepository courseRepository;

    @Override
    public Page<EnrollmentResp> findAll(int page, int size, SortType sortType) {
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

        return enrollmentRepository.findAll(pageable).map(enrollment -> EnrollmentHelper.enrollmentToResp(enrollment));
    }

    @Override
    public EnrollmentResp findByIdWithDetails(Long id) {
        return EnrollmentHelper.enrollmentToResp(findById(id));
    }

    @Override
    public EnrollmentResp create(EnrollmentReq request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("course")));
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("user")));
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        return EnrollmentHelper.enrollmentToResp(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentResp update(EnrollmentReq request, Long id) {
        return EnrollmentResp.builder()
        .build();
    }

    @Override
    public void delete(Long id) {
        enrollmentRepository.delete(findById(id));
    }

    private Enrollment findById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("enrollment")));
    }

}
