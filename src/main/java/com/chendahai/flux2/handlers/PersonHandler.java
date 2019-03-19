package com.chendahai.flux2.handlers;

import com.chendahai.flux2.bean.Person;
import com.chendahai.flux2.repository.PersonRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PersonHandler {

    private final PersonRepository repository;

    public PersonHandler(PersonRepository repository){
        this.repository = repository;
    }

    // 得到所有学生
    public Mono<ServerResponse> getAllPerson(ServerRequest request){
        Mono<ServerResponse> mono = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.findAll(), Person.class);
        return mono;
    }

    // 根据id获取学生
    public Mono<ServerResponse> getPersonById(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<ServerResponse> mono = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.findById(id), Person.class);
        return mono;
    }


    // 创建学生
    public Mono<ServerResponse> createPerson(ServerRequest request){
        Mono<Person> person = request.bodyToMono(Person.class);
        Mono<ServerResponse> mono = person.flatMap(u -> {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(repository.save(u), Person.class);
        });
        return mono;
    }

    // 根据id删除学生
    public Mono<ServerResponse> deletePersonById(ServerRequest request){
        String id = request.pathVariable("id");
        Mono<ServerResponse> mono = repository.findById(id)
                .flatMap(u -> repository.delete(u).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
        return mono;
    }

}
