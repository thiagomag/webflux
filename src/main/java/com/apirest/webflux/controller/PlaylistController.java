package com.apirest.webflux.controller;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping
    public Flux<Playlist> getPlaylist() {
        return playlistService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Playlist> getPlaylistById(@PathVariable String id) {
        return playlistService.findById(id);
    }

    @PostMapping
    public Mono<Playlist> savePlaylist(@RequestBody Playlist playlist) {
        return playlistService.save(playlist);
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> getPlaylistByEvents() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
        Flux<Playlist> events = playlistService.findAll();
        System.out.println("Passou aqui events");
        return Flux.zip(interval, events);
    }
}
