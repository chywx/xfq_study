package com.chendahai.flux.bean;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
public class User {

    @Id
    private String id;

    @NotBlank
    private String name;

    @Range(min=10, max=100)
    private int age;

}

