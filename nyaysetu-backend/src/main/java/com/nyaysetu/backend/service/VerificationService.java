package com.nyaysetu.backend.service;

import com.nyaysetu.backend.dto.CreateVerificationRequest;
import com.nyaysetu.backend.entity.User;
import com.nyaysetu.backend.entity.VerificationRequest;
import com.nyaysetu.backend.entity.VerificationStatus;
import com.nyaysetu.backend.repository.UserRepository;
import com.nyaysetu.backend.repository.VerificationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRequestRepository repository;
    private final UserRepository userRepository;

    public VerificationRequest createRequest(CreateVerificationRequest dto) {
        var request = VerificationRequest.builder()
                .userId(Long.parseLong(dto.getUserId()))
                .requestedRole(dto.getRequestedRole())
                .documentUrls(dto.getDocumentUrls())
                .status(VerificationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        return repository.save(request);
    }

    public List<VerificationRequest> getPending() {
        return repository.findByStatus(VerificationStatus.PENDING);
    }

    @Transactional
    public VerificationRequest approve(UUID id) {
        VerificationRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus(VerificationStatus.APPROVED);
        request.setVerifiedAt(LocalDateTime.now());
        
        // Directly update the user's role in database
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        try {
            com.nyaysetu.backend.entity.Role role = com.nyaysetu.backend.entity.Role.valueOf(request.getRequestedRole());
            user.setRole(role);
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid requested role: " + request.getRequestedRole());
        }

        return repository.save(request);
    }

    public VerificationRequest reject(UUID id) {
        var request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus(VerificationStatus.REJECTED);
        request.setVerifiedAt(LocalDateTime.now());
        
        return repository.save(request);
    }
}
