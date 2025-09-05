package com.my.watermelon.repository;

import com.my.watermelon.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<SongEntity, Long> {
    List<SongEntity> findByTitleContaining(String keyword);
    List<SongEntity> findByArtistContaining(String artist);
}