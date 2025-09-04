package com.my.watermelon.service;

import com.my.watermelon.dto.NoticeDto;
import com.my.watermelon.entity.Notice;
import com.my.watermelon.entity.User;
import com.my.watermelon.repository.NoticeRepository;
import com.my.watermelon.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public Page<Notice> listNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return noticeRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Notice getNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다"));
    }

    public Notice createNotice(NoticeDto dto, String adminUsername) {
        User admin = userRepository.findByUsername(adminUsername).orElseThrow(() -> new IllegalArgumentException("관리자 계정이 없습니다"));
        Notice n = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(admin)
                .build();
        return noticeRepository.save(n);
    }

    public Notice updateNotice(Long id, NoticeDto dto) {
        Notice n = getNotice(id);
        n.setTitle(dto.getTitle());
        n.setContent(dto.getContent());
        return noticeRepository.save(n);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }
}
