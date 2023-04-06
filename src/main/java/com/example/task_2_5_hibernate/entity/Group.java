package com.example.task_2_5_hibernate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer id;
    @Column(name = "group_name")
    private String name;

    public Group(String name) {
        this.name = name;
    }

    public Group(int id) {
        this.id = id;
    }

}

