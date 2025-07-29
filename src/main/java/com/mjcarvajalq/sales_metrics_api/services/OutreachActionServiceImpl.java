package com.mjcarvajalq.sales_metrics_api.services;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionResponse;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDetailResponse;
import com.mjcarvajalq.sales_metrics_api.exceptions.OutreachActionNotFoundException;
import com.mjcarvajalq.sales_metrics_api.exceptions.UserNotFoundException;
import com.mjcarvajalq.sales_metrics_api.model.ActionType;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.model.User;
import com.mjcarvajalq.sales_metrics_api.repositories.OutreachActionRepository;
import com.mjcarvajalq.sales_metrics_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutreachActionServiceImpl implements OutreachActionService{

    private final OutreachActionRepository outreachActionRepository;
    private final UserRepository userRepository;

    @Override
    public CreateOutreachActionResponse saveAction(CreateOutreachActionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        ActionType actionType = request.getType();

        LocalDateTime actionDate = request.getDateTime();

        OutreachAction action = OutreachAction.builder()
                .type(actionType)
                .dateTime(actionDate)
                .notes(request.getNotes())
                .user(user)
                .build();

        OutreachAction savedAction = outreachActionRepository.save(action);
        
        return mapToCreateResponse(savedAction);
    }

    @Override
    public List<OutreachActionDTO> getAllActions() {
        return outreachActionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<OutreachActionDTO> getActionsByUserId(Long userId) {
        return outreachActionRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public OutreachActionDetailResponse getActionById(Long id){
        OutreachAction action = outreachActionRepository.findById(id)
                .orElseThrow(() -> new OutreachActionNotFoundException(id));
        return mapToDetailResponse(action);
    }



    private OutreachActionDTO mapToDTO(OutreachAction action) {
        return OutreachActionDTO.builder()
                .userId(action.getUser().getId())
                .type(action.getType())
                .dateTime(action.getDateTime())
                .notes(action.getNotes())
                .build();
    }
    
    private CreateOutreachActionResponse mapToCreateResponse(OutreachAction action) {
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

    private OutreachActionDetailResponse mapToDetailResponse(OutreachAction action) {
        return OutreachActionDetailResponse.builder()
                .id(action.getId())
                .userId(action.getUser().getId())
                .userName(action.getUser().getName())
                .type(action.getType().name())
                .dateTime(action.getDateTime())
                .notes(action.getNotes())
                .build();

    }

}
