package com.mjcarvajalq.sales_metrics_api.services;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.exceptions.UserNotFoundException;
import com.mjcarvajalq.sales_metrics_api.mappers.OutreachActionMapper;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.model.User;
import com.mjcarvajalq.sales_metrics_api.repositories.OutreachActionRepository;
import com.mjcarvajalq.sales_metrics_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutreachActionServiceImpl implements OutreachActionService{

    private final OutreachActionRepository outreachActionRepository;
    private final UserRepository userRepository;
    private final OutreachActionMapper outreachActionMapper;

    @Override
    public OutreachAction saveAction(CreateOutreachActionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        OutreachAction action = outreachActionMapper.toEntity(request, user);

        return outreachActionRepository.save(action);
    }

    @Override
    public List<OutreachActionDTO> getAllActions() {
        return outreachActionRepository.findAll()
                .stream()
                .map(outreachActionMapper::toDTO)
                .toList();
    }

    @Override
    public List<OutreachActionDTO> getActionsByUserId(Long userId) {
        return outreachActionRepository.findByUserId(userId)
                .stream()
                .map(outreachActionMapper::toDTO)
                .toList();
    }
}
