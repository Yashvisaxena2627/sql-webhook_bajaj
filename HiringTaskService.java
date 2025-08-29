package com.healthrx.hiringtask.service;

import com.healthrx.hiringtask.dto.WebhookRequest;
import com.healthrx.hiringtask.dto.WebhookResponse;
import com.healthrx.hiringtask.dto.SolutionRequest;
import com.healthrx.hiringtask.dto.SolutionResponse;
import com.healthrx.hiringtask.model.Problem;
import com.healthrx.hiringtask.model.Solution;
import com.healthrx.hiringtask.repository.ProblemRepository;
import com.healthrx.hiringtask.repository.SolutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HiringTaskService {
    
    private static final Logger logger = LoggerFactory.getLogger(HiringTaskService.class);
    
    private static final String WEBHOOK_GENERATION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SOLUTION_SUBMISSION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
    
    private final RestTemplate restTemplate;
    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;
    private final SqlProblemSolver sqlProblemSolver;
    
    @Autowired
    public HiringTaskService(RestTemplate restTemplate, 
                           ProblemRepository problemRepository,
                           SolutionRepository solutionRepository,
                           SqlProblemSolver sqlProblemSolver) {
        this.restTemplate = restTemplate;
        this.problemRepository = problemRepository;
        this.solutionRepository = solutionRepository;
        this.sqlProblemSolver = sqlProblemSolver;
    }
    
    public void executeHiringTask() {
        try {
            logger.info("Starting hiring task execution...");
            
            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            if (webhookResponse == null) {
                logger.error("Failed to generate webhook. Exiting task.");
                return;
            }
            
            logger.info("Webhook generated successfully: {}", webhookResponse.getWebhook());
            logger.info("Access token received: {}", webhookResponse.getAccessToken());
            
            // Step 2: Solve SQL problem based on registration number
            String regNo = "REG12347"; // Last two digits: 47 (odd)
            String questionUrl = determineQuestionUrl(regNo);
            logger.info("Question URL determined: {}", questionUrl);
            
            // Step 3: Solve the SQL problem
            String sqlSolution = sqlProblemSolver.solveProblem(questionUrl);
            if (sqlSolution == null) {
                logger.error("Failed to solve SQL problem. Exiting task.");
                return;
            }
            
            logger.info("SQL problem solved successfully. Solution: {}", sqlSolution);
            
            // Step 4: Submit solution
            boolean submissionSuccess = submitSolution(webhookResponse.getAccessToken(), sqlSolution);
            if (submissionSuccess) {
                logger.info("Solution submitted successfully!");
            } else {
                logger.error("Failed to submit solution.");
            }
            
        } catch (Exception e) {
            logger.error("Error during hiring task execution", e);
        }
    }
    
    private WebhookResponse generateWebhook() {
        try {
            WebhookRequest request = new WebhookRequest();
            request.setName("John Doe");
            request.setRegNo("REG12347");
            request.setEmail("john@example.com");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                WEBHOOK_GENERATION_URL, entity, WebhookResponse.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                logger.error("Failed to generate webhook. Status: {}", response.getStatusCode());
                return null;
            }
            
        } catch (Exception e) {
            logger.error("Error generating webhook", e);
            return null;
        }
    }
    
    private String determineQuestionUrl(String regNo) {
        // Extract last two digits from registration number
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastDigits = Integer.parseInt(lastTwoDigits);
        
        if (lastDigits % 2 == 1) { // Odd number (47)
            return "https://drive.google.com/file/d/1IeSI616KoSQAFfRihIT9tEDICtoz-G/view?usp=sharing";
        } else { // Even number
            return "https://drive.google.com/file/d/143MR5cLFrlNEuHzzWJ5RHnEWuijuM9X/view?usp=sharing";
        }
    }
    
    private boolean submitSolution(String accessToken, String sqlSolution) {
        try {
            SolutionRequest request = new SolutionRequest();
            request.setFinalQuery(sqlSolution);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);
            
            HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<SolutionResponse> response = restTemplate.postForEntity(
                SOLUTION_SUBMISSION_URL, entity, SolutionResponse.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Solution submitted successfully. Response: {}", response.getBody());
                return true;
            } else {
                logger.error("Failed to submit solution. Status: {}", response.getStatusCode());
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Error submitting solution", e);
            return false;
        }
    }
}
