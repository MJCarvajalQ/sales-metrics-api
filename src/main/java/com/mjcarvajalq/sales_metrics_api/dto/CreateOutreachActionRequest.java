package com.mjcarvajalq.sales_metrics_api.dto;

import com.mjcarvajalq.sales_metrics_api.model.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Request DTO for creating a new outreach action.
 * This separates the API contract from the internal data model.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOutreachActionRequest {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Action type is required")
    private ActionType type;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    private String notes; // Optional field
}
