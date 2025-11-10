package com.example.news.news_letter_back.service;

import com.example.news.news_letter_back.entity.Subscriber;
import com.example.news.news_letter_back.repository.SubscriberRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Transactional
    public ResponseEntity<?> subscribe(String email) {
        // 1. 이메일 형식 검사 (간단하게 contains로만 체크)
        if (email == null || !email.contains("@")) {
            return ResponseEntity.badRequest().body("이메일 형식이 올바르지 않습니다.");
        }

        // 2. 기존 구독 여부 확인
        Optional<Subscriber> existing = subscriberRepository.findByEmail(email);
        if (existing.isPresent()) {
            return ResponseEntity.status(204).build(); // 이미 구독 중
        }

        // 3. 새로 구독 저장
        Subscriber newSubscriber = Subscriber.builder()
                .email(email)
                .statusAcode("SUBSCRIBE")
                .statusBcode("SUB")
                .unsubscribeToken(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        subscriberRepository.save(newSubscriber);

        System.out.println("✅ DB 저장 완료: " + newSubscriber.getEmail());

        return ResponseEntity.ok("구독이 완료되었습니다.");
    }
}