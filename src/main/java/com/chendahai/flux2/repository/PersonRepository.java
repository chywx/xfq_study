package com.chendahai.flux2.repository;

import com.chendahai.flux.bean.User;
import com.chendahai.flux2.bean.Person;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

    Flux<User> findByAgeBetween(int start, int end);

    @Query("{'age':{'$gte':20,'$lte':30}}")
    Flux<User> oldUser();
}
