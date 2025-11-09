package com.java.microservice.dto;

// Standard Java class to represent the Kafka message
public class ApiCallRequest {
    private String resourceId;
    private String payload;

    // Default constructor is necessary for JSON deserialization
    public ApiCallRequest() {}

    public ApiCallRequest(String resourceId, String payload) {
        this.resourceId = resourceId;
        this.payload = payload;
    }

    // Getters and Setters
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ApiCallRequest{resourceId='" + resourceId + "', payload='" + payload + "'}";
    }
}
