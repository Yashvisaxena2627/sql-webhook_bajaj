package com.healthrx.hiringtask.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solutions")
public class Solution {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String problemId;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String sqlQuery;
    
    @Column(nullable = false)
    private String status;
    
    @Column(columnDefinition = "TEXT")
    private String response;
    
    @Column(nullable = false)
    private LocalDateTime submittedAt;
    
    public Solution() {}
    
    public Solution(Long id, String problemId, String sqlQuery, String status, String response, LocalDateTime submittedAt) {
        this.id = id;
        this.problemId = problemId;
        this.sqlQuery = sqlQuery;
        this.status = status;
        this.response = response;
        this.submittedAt = submittedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProblemId() { return problemId; }
    public void setProblemId(String problemId) { this.problemId = problemId; }
    
    public String getSqlQuery() { return sqlQuery; }
    public void setSqlQuery(String sqlQuery) { this.sqlQuery = sqlQuery; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}
