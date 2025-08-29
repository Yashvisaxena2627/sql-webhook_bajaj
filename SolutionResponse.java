package com.healthrx.hiringtask.dto;

public class SolutionResponse {
    private String message;
    private String status;
    private String details;
    
    public SolutionResponse() {}
    
    public SolutionResponse(String message, String status, String details) {
        this.message = message;
        this.status = status;
        this.details = details;
    }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
