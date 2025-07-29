package com.mjcarvajalq.sales_metrics_api.mappers;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionResponse;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDetailResponse;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper component responsible for converting between OutreachAction entities and DTOs.
 * This component isolates the mapping logic from the service layer, following the 
 * Single Responsibility Principle and making the code more maintainable.
 * 
 * Future enhancement: This will be replaced with MapStruct in Issue #6 for 
 * automatic mapping generation and better performance.
 */
@Component
public class OutreachActionMapper {
    public OutreachActionDTO toDTO(OutreachAction action) {
        if (action == null) {
            throw new IllegalArgumentException("OutreachAction cannot be null");
        }

        return OutreachActionDTO.builder()
                .userId(action.getUser().getId())
                .type(action.getType())
                .dateTime(action.getDateTime())
                .notes(action.getNotes())
                .build();
    }

    public OutreachAction toEntity(CreateOutreachActionRequest request, User user) {
        if (request == null) {
            throw new IllegalArgumentException("CreateOutreachActionRequest cannot be null");
        }
        if (user == null) {
            throw  new IllegalArgumentException("User cannot be null");
        }

        return OutreachAction.builder()
                .type(request.getType())
                .dateTime(request.getDateTime())
                .notes(request.getNotes())
                .user(user)
                .build();
    }

    public OutreachActionDetailResponse mapToDetailResponse(OutreachAction action) {
        if (action == null){
            throw new IllegalArgumentException("OutreachAction cannot be null");
        }
        return OutreachActionDetailResponse.builder()
                .id(action.getId())
                .userId(action.getUser().getId())
                .userName(action.getUser().getName())
                .type(action.getType().name())
                .dateTime(action.getDateTime())
                .notes(action.getNotes())
                .build();

    }

    public CreateOutreachActionResponse mapToCreateResponse(OutreachAction action) {
        if (action == null) {
            throw new IllegalArgumentException("Outreach action cannot be null");
        }

        return CreateOutreachActionResponse.builder()
                .id(action.getId())
                .userId(action.getUser().getId())
                .userName(action.getUser().getName())
                .type(action.getType())
                .dateTime(action.getDateTime())
                .notes(action.getNotes())
                .message("Outreach action created successfully")
                .build();

    }

}
