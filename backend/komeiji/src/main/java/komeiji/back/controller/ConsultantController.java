package komeiji.back.controller;

import komeiji.back.dto.ConsultantRequestDTO;
import komeiji.back.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultant")
public class ConsultantController {

    @Autowired
    private ConsultantService consultantService;

    @PostMapping("/select")
    public ResponseEntity<?> selectConsultant(@RequestBody ConsultantRequestDTO request) {
        try {
            consultantService.handleConsultantRequest(request.getConsultantId(), request.getUserId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 