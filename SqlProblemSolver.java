package com.healthrx.hiringtask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SqlProblemSolver {
    
    private static final Logger logger = LoggerFactory.getLogger(SqlProblemSolver.class);
    
    // Pre-defined solutions for common SQL problems
    private final Map<String, String> problemSolutions;
    
    public SqlProblemSolver() {
        this.problemSolutions = initializeProblemSolutions();
    }
    
    public String solveProblem(String questionUrl) {
        try {
            logger.info("Solving SQL problem for URL: {}", questionUrl);
            
            // Extract problem identifier from URL
            String problemId = extractProblemId(questionUrl);
            logger.info("Problem ID extracted: {}", problemId);
            
            // Get solution from pre-defined solutions
            String solution = problemSolutions.get(problemId);
            if (solution != null) {
                logger.info("Solution found: {}", solution);
                return solution;
            }
            
            // If no pre-defined solution, analyze the problem dynamically
            return analyzeProblemDynamically(questionUrl);
            
        } catch (Exception e) {
            logger.error("Error solving SQL problem", e);
            return null;
        }
    }
    
    private String extractProblemId(String url) {
        // Extract file ID from Google Drive URL
        if (url.contains("1IeSI616KoSQAFfRihIT9tEDICtoz-G")) {
            return "PROBLEM_1";
        } else if (url.contains("143MR5cLFrlNEuHzzWJ5RHnEWuijuM9X")) {
            return "PROBLEM_2";
        }
        return "UNKNOWN";
    }
    
    private String analyzeProblemDynamically(String questionUrl) {
        // This method would analyze the actual problem content
        // For now, return the correct solution based on the URL pattern
        
        if (questionUrl.contains("1IeSI616KoSQAFfRihIT9tEDICtoz-G")) {
            // Problem 1 (Odd registration number) - Count younger employees in same department
            return "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, " +
                   "COUNT(CASE WHEN e2.DOB > e1.DOB THEN 1 END) AS YOUNGER_EMPLOYEES_COUNT " +
                   "FROM EMPLOYEE e1 " +
                   "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                   "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.EMP_ID != e1.EMP_ID " +
                   "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                   "ORDER BY e1.EMP_ID DESC";
        } else if (questionUrl.contains("143MR5cLFrlNEuHzzWJ5RHnEWuijuM9X")) {
            // Problem 2 (Even registration number) - Find highest salary excluding 1st day transactions
            return "SELECT p.AMOUNT AS SALARY, " +
                   "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
                   "YEAR(CURDATE()) - YEAR(e.DOB) AS AGE, " +
                   "d.DEPARTMENT_NAME " +
                   "FROM PAYMENTS p " +
                   "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                   "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                   "WHERE DAY(p.PAYMENT_TIME) != 1 " +
                   "ORDER BY p.AMOUNT DESC " +
                   "LIMIT 1";
        }
        
        // Default generic solution
        return "SELECT * FROM table_name WHERE 1=1";
    }
    
    private Map<String, String> initializeProblemSolutions() {
        Map<String, String> solutions = new HashMap<>();
        
        // Problem 1: Odd registration number (47) - Count younger employees in same department
        solutions.put("PROBLEM_1", 
            "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, " +
            "COUNT(CASE WHEN e2.DOB > e1.DOB THEN 1 END) AS YOUNGER_EMPLOYEES_COUNT " +
            "FROM EMPLOYEE e1 " +
            "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
            "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.EMP_ID != e1.EMP_ID " +
            "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
            "ORDER BY e1.EMP_ID DESC");
        
        // Problem 2: Even registration number - Find highest salary excluding 1st day transactions
        solutions.put("PROBLEM_2", 
            "SELECT p.AMOUNT AS SALARY, " +
            "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
            "YEAR(CURDATE()) - YEAR(e.DOB) AS AGE, " +
            "d.DEPARTMENT_NAME " +
            "FROM PAYMENTS p " +
            "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
            "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
            "WHERE DAY(p.PAYMENT_TIME) != 1 " +
            "ORDER BY p.AMOUNT DESC " +
            "LIMIT 1");
        
        return solutions;
    }
}
