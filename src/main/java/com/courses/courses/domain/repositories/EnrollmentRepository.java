package com.courses.courses.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courses.courses.domain.entities.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long>{

    public List<Enrollment> findByStudentId(Long id);
    public List<Enrollment> findByCourseId(Long id);

}
