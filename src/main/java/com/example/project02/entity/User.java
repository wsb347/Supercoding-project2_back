package com.example.project02.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 45, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    private String phone;

    private String address;

    private String gender;

    private String status;

    private String img;
}
