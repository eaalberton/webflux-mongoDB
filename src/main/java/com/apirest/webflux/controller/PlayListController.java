package com.apirest.webflux.controller;

import com.apirest.webflux.document.PlayList;
import com.apirest.webflux.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
public class PlayListController {

    @Autowired
    PlayListService playListService;

    @GetMapping(value = "/findAllPlayList")
    public Flux<PlayList> findAllPlayList(){
        return playListService.findAll();
    }

    @GetMapping(value = "/findPlayListById/{id}")
    public Mono<PlayList> findPlayListById(@PathVariable String id){
        return playListService.findById(id);
    }

    @PostMapping(value = "/save")
    public Mono<PlayList> savePlayList(@RequestBody  PlayList playList){
        return playListService.save(playList);
    }

    //USADO PARA TESTAR API REATIVA, FAZENDO UMA REQUISIÇÃO DE TODAS AS PLAYLISTS UMA POR UMA
    //A CADA 10 SEGUNDOS, E NÃO BLOQUEANDO OUTRAS CHAMADAS MESMO NÃO TENDO TERMIDADO A 1° CHAMADA
    @GetMapping(value="/playlist/webflux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, PlayList>> getPlaylistByWebflux(){

        System.out.println("---Start get Playlists by WEBFLUX--- " + LocalDateTime.now());
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
        Flux<PlayList> playlistFlux = playListService.findAll();

        return Flux.zip(interval, playlistFlux);
    }

}
