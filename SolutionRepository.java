package com.healthrx.hiringtask.repository;

import com.healthrx.hiringtask.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    
    Optional<Solution> findByProblemId(String problemId);
    
    List<Solution> findByStatus(String status);
    
    List<Solution> findByProblemIdOrderBySubmittedAtDesc(String problemId);
}
