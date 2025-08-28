package com.mjcarvajalq.sales_metrics_api.controllers;

import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionResponse;
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
    public ResponseEntity<List<OutreachActionResponse>> getUserActions(@PathVariable Long userId) {
        List<OutreachActionResponse> actions = outreachActionService.getActionsByUserId(userId);
        return ResponseEntity.ok(actions);
    }
}
