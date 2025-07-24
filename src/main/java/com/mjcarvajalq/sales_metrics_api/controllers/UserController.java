package com.mjcarvajalq.sales_metrics_api.controllers;

import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.services.OutreachActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final OutreachActionService outreachActionService;

    @GetMapping("/{userId}/actions")
    public ResponseEntity<List<OutreachActionDTO>> getUserActions(@PathVariable Long userId) {
        List<OutreachActionDTO> actions = outreachActionService.getActionsByUserId(userId);
        return ResponseEntity.ok(actions);
    }
}
