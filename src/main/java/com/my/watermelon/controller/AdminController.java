package com.my.watermelon.controller;

import com.my.watermelon.dto.NoticeDto;
import com.my.watermelon.dto.UserAdminDto;
import com.my.watermelon.entity.Notice;
import com.my.watermelon.service.NoticeService;
import com.my.watermelon.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final NoticeService noticeService;
    private final UserService userService;

    // 관리자 홈 페이지
    @GetMapping({"/",""})
    public String adminHome() {
        return "admin/admin_home"; // admin_home.html
    }

    // 공지 리스트
    @GetMapping("/notices")
    public String noticeList(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Notice> notices = noticeService.listNotices(page, 10);
        model.addAttribute("notices", notices);
        return "admin/notice_list";
    }

    // 공지 작성 폼
    @GetMapping("/notices/new")
    public String newNoticeForm(Model model) {
        model.addAttribute("noticeDto", new NoticeDto());
        return "admin/notice_form";
    }

    // 공지 저장
    @PostMapping("/notices")
    public String createNotice(@Valid @ModelAttribute("noticeDto") NoticeDto dto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "admin/notice_form";
        }
        noticeService.createNotice(dto, userDetails.getUsername());
        return "redirect:/admin/notices";
    }

    // 공지 수정 폼
    @GetMapping("/notices/{id}/edit")
    public String editNoticeForm(@PathVariable Long id, Model model) {
        Notice n = noticeService.getNotice(id);
        NoticeDto dto = NoticeDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .build();
        model.addAttribute("noticeDto", dto);
        return "admin/notice_form";
    }

    @PostMapping("/notices/{id}/edit")
    public String updateNotice(@PathVariable Long id,
                               @Valid @ModelAttribute("noticeDto") NoticeDto dto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/notice_form";
        }
        noticeService.updateNotice(id, dto);
        return "redirect:/admin/notices";
    }

    // 공지 삭제
    @PostMapping("/notices/{id}/delete")
    public String deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/admin/notices";
    }

    // 사용자 관리 페이지
    @GetMapping("/users")
    public String users(Model model) {
        List<UserAdminDto> users = userService.listAllUsers();
        model.addAttribute("users", users);
        return "admin/user_list";
    }

    @PostMapping("/users/{id}/disable")
    public String disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/enable")
    public String enableUser(@PathVariable Long id) {
        userService.enableUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
