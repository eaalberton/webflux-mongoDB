package com.apirest.webflux;

import com.apirest.webflux.document.PlayList;
import com.apirest.webflux.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

//COMENTADO POIS FORAM FEITOS DOIS TIPOS DE ENDPOINT PARA TESTES
//@Component
public class PlayListHandler {

    @Autowired
    PlayListService playListService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(playListService.findAll(), PlayList.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){

        String id = request.pathVariable("id");

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(playListService.findById(id), PlayList.class);
    }

    public Mono<ServerResponse> save(ServerRequest request){

        final Mono<PlayList> playList = request.bodyToMono(PlayList.class);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(playList.flatMap(playListService::save), PlayList.class));
    }

}
