package com.healthrx.hiringtask.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "problems")
public class Problem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String problemId;
    
    @Column(nullable = false)
    private String questionUrl;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private String registrationNumber;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    public Problem() {}
    
    public Problem(Long id, String problemId, String questionUrl, String description, String registrationNumber, LocalDateTime createdAt) {
        this.id = id;
        this.problemId = problemId;
        this.questionUrl = questionUrl;
        this.description = description;
        this.registrationNumber = registrationNumber;
        this.createdAt = createdAt;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProblemId() { return problemId; }
    public void setProblemId(String problemId) { this.problemId = problemId; }
    
    public String getQuestionUrl() { return questionUrl; }
    public void setQuestionUrl(String questionUrl) { this.questionUrl = questionUrl; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
