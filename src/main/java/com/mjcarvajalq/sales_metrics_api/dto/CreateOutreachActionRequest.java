package com.mjcarvajalq.sales_metrics_api.dto;

import com.mjcarvajalq.sales_metrics_api.model.ActionType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Request DTO for creating a new outreach action.
 * This separates the API contract from the internal data model.
 */
@Data
public class CreateOutreachActionRequest {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Action type is required")
    private ActionType type;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    private String notes; // Optional field
}
