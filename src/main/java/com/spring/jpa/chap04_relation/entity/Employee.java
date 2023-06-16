package com.spring.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Setter
@Getter@EqualsAndHashCode
@NoArgsConstructor@AllArgsConstructor
@Builder
@Table(name="tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id")
    private long id;

    @Column(name ="emp_name", nullable = false)
    private String name;

}
