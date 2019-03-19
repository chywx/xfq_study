package com.chendahai.flux2.routers;

import com.chendahai.flux2.handlers.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

//import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
//import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
//import static org.springframework.web.reactive.function.server.RequestPredicates.path;
//import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> PersonRouter(PersonHandler handler) {
        RouterFunction<ServerResponse> function = RouterFunctions.nest(
                RequestPredicates.path("/person"),
                // 查询全部
                RouterFunctions.route(RequestPredicates.GET("/"), handler::getAllPerson)
                        // 根据id查询
                        .andRoute(RequestPredicates.GET("/{id}"),handler::getPersonById)
                        // 添加person
                        .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), handler::createPerson)
                        // 根据id删除person
                        .andRoute(RequestPredicates.DELETE("/{id}"), handler::deletePersonById));
        return function;
    }




//    @Bean
//    RouterFunction<ServerResponse> PersonRouter(PersonHandler handler) {
//        return nest(
//                // 相当于类上面的 @RequestMapping("/user")
//                path("/person"),
//                // 下面的相当于类里面的 @RequestMapping
//                // 得到所有用户
//                route(GET("/"), handler::getAllPerson)
//                        // 创建用户
//                        .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON_UTF8)),
//                                handler::createPerson)
//                        // 删除用户
//                        .andRoute(DELETE("/{id}"), handler::deletePersonById)
//                        // 查询用户
//                        .andRoute(GET("/{id}"), handler::getPersonById)
//        );
//    }



}
