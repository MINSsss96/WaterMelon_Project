package com.my.watermelon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
    private Long id;

    @NotBlank(message = "제목을 입력하세요")
    @Size(max = 200)
    private String title;

    @NotBlank(message = "내용을 입력하세요")
    private String content;
}
