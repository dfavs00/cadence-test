package com.favata.cadencetest.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class TaskUser {
    private User user;
    private List<Task> tasks;
}
