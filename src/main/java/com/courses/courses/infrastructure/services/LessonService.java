package com.courses.courses.infrastructure.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.courses.courses.api.dto.request.LessonReq;
import com.courses.courses.api.dto.response.ActivityResp;
import com.courses.courses.api.dto.response.LessonResp;
import com.courses.courses.api.dto.response.LessonRespWithActivities;
import com.courses.courses.domain.entities.Course;
import com.courses.courses.domain.entities.Lesson;
import com.courses.courses.domain.repositories.ActivityRepository;
import com.courses.courses.domain.repositories.CourseRepository;
import com.courses.courses.domain.repositories.LessonRepository;
import com.courses.courses.infrastructure.abstract_services.ILessonService;
import com.courses.courses.infrastructure.helper.ActivityHelper;
import com.courses.courses.infrastructure.helper.LessonHelper;
import com.courses.courses.utils.enums.SortType;
import com.courses.courses.utils.exception.BadRequestException;
import com.courses.courses.utils.message.ErrorMessage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {

    @Autowired
    private final LessonRepository lessonRepository;
    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private final ActivityRepository activityRepository;

    @Override
    public Page<LessonResp> findAll(int page, int size, SortType sortType) {
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
        return lessonRepository.findAll(pageable).map(lesson -> LessonHelper.lessonToResp(lesson));
    }

    @Override
    public LessonResp findByIdWithDetails(Long id) {
        return LessonHelper.lessonToResp(findById(id));
    }

    @Override
    public LessonResp create(LessonReq request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("course")));
        Lesson lesson = LessonHelper.reqToLesson(request);
        lesson.setCourse(course);
        return LessonHelper.lessonToResp(lessonRepository.save(lesson));
    }

    @Override
    public LessonResp update(LessonReq request, Long id) {
        Lesson lessonFound = findById(id);
        Lesson lesson = LessonHelper.reqToLesson(request);
        lesson.setId(id);
        lesson.setCourse(lessonFound.getCourse());
        return LessonHelper.lessonToResp(lessonRepository.save(lesson));
    }

    @Override
    public void delete(Long id) {
        lessonRepository.delete(findById(id));
    }

    private Lesson findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("lesson")));
    }

    @Override
    public LessonRespWithActivities getLessonWithActivities(Long id) {
        List<ActivityResp> activities = activityRepository.findByLessonId(id).stream()
                .map(activity -> ActivityHelper.activityToResp(activity)).toList();
        Lesson lesson = findById(id);

        return LessonRespWithActivities.builder()
        .id(lesson.getId())
        .lessonTitle(lesson.getLessonTitle())
        .content(lesson.getContent())
        .activities(activities)
        .build();
    }

}
