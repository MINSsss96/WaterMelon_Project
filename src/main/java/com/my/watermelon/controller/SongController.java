package com.my.watermelon.controller;

import com.my.watermelon.dto.SongDto;
import com.my.watermelon.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public SongDto createSong(@RequestBody SongDto dto) {
        return songService.saveSong(dto);
    }

    @GetMapping
    public List<SongDto> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    public SongDto getSong(@PathVariable Long id) {
        return songService.getSong(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
    }

    @GetMapping("/list")
    public String songList(Model model) {
        List<SongDto> list = songService.findAllUser();
        model.addAttribute("list", list);
        return "songList";
    }


}