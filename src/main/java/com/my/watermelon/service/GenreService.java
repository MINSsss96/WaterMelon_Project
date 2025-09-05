package com.my.watermelon.service;

import com.my.watermelon.dto.GenreDto;
import com.my.watermelon.entity.GenreEntity;
import com.my.watermelon.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    // 장르 등록
    public GenreDto createGenre(GenreDto dto) {
        // 이름 중복 방지
        if (genreRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Genre already exists: " + dto.getName());
        }

        GenreEntity genre = GenreEntity.builder()
                .name(dto.getName())
                .build();

        GenreEntity saved = genreRepository.save(genre);

        return convertToDto(saved);
    }

    // 전체 장르 조회
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 특정 장르 조회
    public GenreDto getGenre(Long id) {
        GenreEntity genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        return convertToDto(genre);
    }

    // 장르 삭제
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    private GenreDto convertToDto(GenreEntity entity) {
        return GenreDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
