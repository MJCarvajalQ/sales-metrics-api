package com.mjcarvajalq.sales_metrics_api.services;

import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.model.User;
import com.mjcarvajalq.sales_metrics_api.repositories.OutreachActionRepository;
import com.mjcarvajalq.sales_metrics_api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutreachActionServiceImpl implements OutreachActionService{

    private OutreachActionRepository outreachActionRepository;
    private final UserRepository userRepository;

    public OutreachActionServiceImpl(OutreachActionRepository outreachActionRepository, UserRepository userRepository) {
        this.outreachActionRepository = outreachActionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OutreachAction saveAction(OutreachActionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        OutreachAction action = new OutreachAction();
        action.setType(dto.getType());
        action.setDate(dto.getDate());
        action.setNotes(dto.getNotes());
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
