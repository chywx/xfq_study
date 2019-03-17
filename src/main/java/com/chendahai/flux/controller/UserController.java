package com.chendahai.flux.controller;

import com.chendahai.flux.bean.User;
import com.chendahai.flux.repository.UserRepository;
import com.chendahai.flux.util.CheckUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    // 不适用注解，使用构造器注入
    private final UserRepository userRepository;

    public UserController(UserRepository repository){
        this.userRepository = repository;
    }

    // 以数组形式一次性返回数据
    @GetMapping("/")
    public Flux<User> getAll(){
        return userRepository.findAll();
    }

    // 以sse形式多次返回数据
    @GetMapping(value = "/stream/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll(){
        return userRepository.findAll();
    }

    // 新增数据
    @PostMapping("/")
    public Mono<User> createUser(@Valid @RequestBody User user){
        // spring data jpa里面，新增修改都是save，有id就表示修改。
        user.setId(null);
        CheckUtil.checkName(user.getName());
        return this.userRepository.save(user);
    }

    // 根据id删除用户，存在的时候返回200，不存在返回404
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id){
        Mono<ResponseEntity<Void>> mono = userRepository.findById(id)
                // flatMap操作数据
                // map修改数据
                .flatMap(user -> userRepository.delete(user).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return mono;
    }

    // 修改数据
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id")String id,@Valid @RequestBody User user){
        CheckUtil.checkName(user.getName());
        Mono<ResponseEntity<User>> mono = userRepository.findById(id)
                .flatMap(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    return this.userRepository.save(u);
                })
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return mono;
    }

    // 根据id查找用户，存在返回用户信息，不存在返回404
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findUserById(@PathVariable("id") String id){
        Mono<ResponseEntity<User>> mono = userRepository.findById(id)
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return mono;
    }

    // 根据年龄段查找用户
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findUserByAge(@PathVariable("start") int start,@PathVariable("end") int end){
        Flux<User> flux = userRepository.findByAgeBetween(start, end);
        return flux;
    }

    // sse形式 根据年龄查找用户
    @GetMapping(value = "/stream/age/{start}/{end}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindUserByAge(@PathVariable("start") int start,@PathVariable("end") int end){
        Flux<User> flux = userRepository.findByAgeBetween(start, end);
        return flux;
    }

    // 得到年龄20-30的用户
    @GetMapping("/old")
    public Flux<User> oldUser(){
        return userRepository.oldUser();
    }

    // sse 得到年龄20-30的用户
    @GetMapping(value = "/stream/old",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamOldUser(){
        return userRepository.oldUser();
    }

}
