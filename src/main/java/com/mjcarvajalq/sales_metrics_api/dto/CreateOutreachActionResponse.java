package com.mjcarvajalq.sales_metrics_api.dto;

import com.mjcarvajalq.sales_metrics_api.model.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for outreach action creation.
 * This provides a clean API response without exposing internal entity structure.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOutreachActionResponse {
    
    private Long id;
    private Long userId;
    private String userName;
    private ActionType type;
    private LocalDateTime date;
    private String notes;
    private String message;
    
}
