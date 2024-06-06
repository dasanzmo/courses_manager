package com.courses.courses.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courses.courses.domain.entities.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long>{

    public List<Submission> findByActivityId(Long id);
    public List<Submission> findByUserId(Long id);

}
