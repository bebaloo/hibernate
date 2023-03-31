package com.example.task_2_5_hibernate.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Group {
    private int id;
    private String name;

    public Group(String name) {
        this.name = name;
    }

    public Group(int id) {
        this.id = id;
    }
}

