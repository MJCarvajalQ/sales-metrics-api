package com.mjcarvajalq.sales_metrics_api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class OutreachActionDetailResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String type;
    private LocalDateTime dateTime;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
