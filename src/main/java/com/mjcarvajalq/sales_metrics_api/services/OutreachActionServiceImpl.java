package com.mjcarvajalq.sales_metrics_api.services;
import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionResponse;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionResponse;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDetailResponse;
import com.mjcarvajalq.sales_metrics_api.exceptions.OutreachActionNotFoundException;
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
    public CreateOutreachActionResponse saveAction(CreateOutreachActionRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("CreateOutreachActionRequest cannot be null");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        OutreachAction action = outreachActionMapper.toEntity(request, user);

        OutreachAction savedAction = outreachActionRepository.save(action);
        
        return outreachActionMapper.mapToCreateResponse(savedAction);
    }

    @Override
    public List<OutreachActionResponse> getAllActions() {
        List<OutreachAction> actions = outreachActionRepository.findAll();
        return actions.stream()
                      .map(outreachActionMapper::toResponse)
                      .toList();
    }

    @Override
    public List<OutreachActionResponse> getActionsByUserId(Long userId) {
        List<OutreachAction> actions = outreachActionRepository.findByUserId(userId);
        return actions.stream()
                      .map(outreachActionMapper::toResponse)
                      .toList();
    }
    @Override
    public OutreachActionDetailResponse getActionById(Long id){
        OutreachAction action = outreachActionRepository.findById(id)
                .orElseThrow(() -> new OutreachActionNotFoundException(id));
        return outreachActionMapper.mapToDetailResponse(action);
    }
}
