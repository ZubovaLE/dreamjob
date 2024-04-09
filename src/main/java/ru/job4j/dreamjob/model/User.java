package ru.job4j.dreamjob.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Include
    private int id;

    @EqualsAndHashCode.Include
    private String email;

    private String name;
    private String password;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
