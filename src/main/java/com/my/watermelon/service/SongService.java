package com.my.watermelon.service;

import com.my.watermelon.dto.SongDto;
import com.my.watermelon.entity.GenreEntity;
import com.my.watermelon.entity.SongEntity;
import com.my.watermelon.repository.GenreRepository;
import com.my.watermelon.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final GenreRepository genreRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public SongDto saveSong(SongDto dto) {
        GenreEntity genre = genreRepository.findByName(dto.getGenreName())
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        SongEntity song = SongEntity.builder()
                .title(dto.getTitle())
                .artist(dto.getArtist())
                .lyrics(dto.getLyrics())
                .album(dto.getAlbum())
                .genre(genre)
                .build();

        SongEntity saved = songRepository.save(song);

        return convertToDto(saved);
    }

    public List<SongDto> getAllSongs() {
        return songRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SongDto getSong(Long id) {
        SongEntity song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        return convertToDto(song);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    private SongDto convertToDto(SongEntity entity) {
        return SongDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .artist(entity.getArtist())
                .album(entity.getAlbum())
                .lyrics(entity.getLyrics())
                .genreName(entity.getGenre().getName())
                .build();
    }
    public List<SongDto> findAllUser() {
        return songRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}