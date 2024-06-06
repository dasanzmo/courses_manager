package com.courses.courses.infrastructure.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.courses.courses.api.dto.request.ActivityReq;
import com.courses.courses.api.dto.response.ActivityResp;
import com.courses.courses.api.dto.response.ActivityRespWithSubmissions;
import com.courses.courses.api.dto.response.SubmissionResp;
import com.courses.courses.domain.entities.Activity;
import com.courses.courses.domain.entities.Lesson;
import com.courses.courses.domain.repositories.ActivityRepository;
import com.courses.courses.domain.repositories.LessonRepository;
import com.courses.courses.domain.repositories.SubmissionRepository;
import com.courses.courses.infrastructure.abstract_services.IActivityService;
import com.courses.courses.infrastructure.helper.ActivityHelper;
import com.courses.courses.infrastructure.helper.SubmissionHelper;
import com.courses.courses.utils.enums.SortType;
import com.courses.courses.utils.exception.BadRequestException;
import com.courses.courses.utils.message.ErrorMessage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivityService implements IActivityService {

    @Autowired
    private final ActivityRepository activityRepository;
    @Autowired
    private final LessonRepository lessonRepository;
    @Autowired
    private final SubmissionRepository submissionRepository;

    @Override
    public Page<ActivityResp> findAll(int page, int size, SortType sortType) {
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
        return activityRepository.findAll(pageable).map(activity -> ActivityHelper.activityToResp(activity));
    }

    @Override
    public ActivityResp findByIdWithDetails(Long id) {
        return ActivityHelper.activityToResp(findById(id));
    }

    @Override
    public ActivityResp create(ActivityReq request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("lesson")));
        Activity activity = ActivityHelper.reqToActivity(request);
        activity.setLesson(lesson);
        return ActivityHelper.activityToResp(activityRepository.save(activity));
    }

    @Override
    public ActivityResp update(ActivityReq request, Long id) {
        Activity activityFound = findById(id);
        Activity activity = ActivityHelper.reqToActivity(request);
        activity.setId(id);
        activity.setLesson(activityFound.getLesson());
        return ActivityHelper.activityToResp(activityRepository.save(activity));

    }

    @Override
    public void delete(Long id) {
        activityRepository.delete(findById(id));
    }

    private Activity findById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("activity")));

    }

    @Override
    public ActivityRespWithSubmissions getActivityWithSubmissions(Long id) {
        List<SubmissionResp> submissions = submissionRepository.findByActivityId(id).stream()
        .map(submission -> SubmissionHelper.submissionToResp(submission)).toList();
        Activity activity = findById(id);
        return ActivityRespWithSubmissions.builder()
        .id(activity.getId())
        .activityTitle(activity.getActivityTitle())
        .description(activity.getDescription())
        .dueDate(activity.getDueDate())
        .submissions(submissions)
        .build();
    }

}
