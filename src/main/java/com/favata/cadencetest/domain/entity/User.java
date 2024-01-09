package com.favata.cadencetest.domain.entity;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class User {
    @NonNull
    private String id;
    private String name;

    public User(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
    }
}
