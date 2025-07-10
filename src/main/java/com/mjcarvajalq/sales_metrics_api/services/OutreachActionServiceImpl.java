package com.mjcarvajalq.sales_metrics_api.services;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.exceptions.UserNotFoundException;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.model.User;
import com.mjcarvajalq.sales_metrics_api.repositories.OutreachActionRepository;
import com.mjcarvajalq.sales_metrics_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutreachActionServiceImpl implements OutreachActionService{

    private final OutreachActionRepository outreachActionRepository;
    private final UserRepository userRepository;

    public OutreachActionServiceImpl(OutreachActionRepository outreachActionRepository, UserRepository userRepository) {
        this.outreachActionRepository = outreachActionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OutreachAction saveAction(CreateOutreachActionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        OutreachAction action = new OutreachAction();
        action.setType(request.getType());
        action.setDate(request.getDate());
        action.setNotes(request.getNotes());
        action.setUser(user);

        return outreachActionRepository.save(action);
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

    private OutreachActionDTO mapToDTO(OutreachAction action) {
        return new OutreachActionDTO(
                action.getUser().getId(),
                action.getType(),
                action.getDate(),
                action.getNotes()
        );

    }
}
