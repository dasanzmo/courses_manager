package com.courses.courses.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.courses.courses.api.dto.request.SubmissionReq;
import com.courses.courses.api.dto.response.SubmissionResp;
import com.courses.courses.domain.entities.Activity;
import com.courses.courses.domain.entities.Submission;
import com.courses.courses.domain.entities.User;
import com.courses.courses.domain.repositories.ActivityRepository;
import com.courses.courses.domain.repositories.SubmissionRepository;
import com.courses.courses.domain.repositories.UserRepository;
import com.courses.courses.infrastructure.abstract_services.ISubmissionService;
import com.courses.courses.infrastructure.helper.SubmissionHelper;
import com.courses.courses.utils.enums.SortType;
import com.courses.courses.utils.exception.BadRequestException;
import com.courses.courses.utils.message.ErrorMessage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubmissionService implements ISubmissionService {

    @Autowired
    private final SubmissionRepository submissionRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ActivityRepository activityRepository;

    @Override
    public Page<SubmissionResp> findAll(int page, int size, SortType sortType) {
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
        return submissionRepository.findAll(pageable).map(submission -> SubmissionHelper.submissionToResp(submission));
    }

    @Override
    public SubmissionResp findByIdWithDetails(Long id) {
        return SubmissionHelper.submissionToResp(findById(id));
    }

    @Override
    public SubmissionResp create(SubmissionReq request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("user")));
        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("activity")));
        Submission submission = SubmissionHelper.reqToSubmission(request);
        submission.setActivity(activity);
        submission.setUser(user);
        return SubmissionHelper.submissionToResp(submissionRepository.save(submission));
    }

    @Override
    public SubmissionResp update(SubmissionReq request, Long id) {
        Submission submissionFound = findById(id);
        Submission submission = SubmissionHelper.reqToSubmission(request);
        submission.setId(id);
        submission.setActivity(submissionFound.getActivity());
        submission.setUser(submissionFound.getUser());
        return SubmissionHelper.submissionToResp(submissionRepository.save(submission));
    }

    @Override
    public void delete(Long id) {
        submissionRepository.delete(findById(id));
    }

    private Submission findById(Long id) {
        return submissionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("submission")));
    }

}
