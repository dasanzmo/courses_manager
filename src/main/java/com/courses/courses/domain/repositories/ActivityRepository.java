package com.courses.courses.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courses.courses.domain.entities.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long>{

    public List<Activity> findByLessonId(Long id);
}
