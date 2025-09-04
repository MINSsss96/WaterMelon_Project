package com.my.watermelon.service;

import com.my.watermelon.dto.UserAdminDto;
import com.my.watermelon.entity.User;
import com.my.watermelon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public List<UserAdminDto> listAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> UserAdminDto.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .name(u.getName())
                        .email(u.getEmail())
                        .enabled(u.isEnabled())
                        .build())
                .collect(Collectors.toList());
    }

    public void disableUser(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        u.setEnabled(false);
        userRepository.save(u);
    }

    public void enableUser(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        u.setEnabled(true);
        userRepository.save(u);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
