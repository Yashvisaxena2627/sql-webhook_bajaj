package com.healthrx.hiringtask.repository;

import com.healthrx.hiringtask.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    
    Optional<Problem> findByProblemId(String problemId);
    
    Optional<Problem> findByRegistrationNumber(String registrationNumber);
}
