package com.chendahai.flux2.bean;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "person")
@Data
public class Person {

    @Id
    private String id;

    @NotBlank
    private String name;

    @Range(min=10, max=100)
    private int age;

}

