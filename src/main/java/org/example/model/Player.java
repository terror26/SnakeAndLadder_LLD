package org.example.model;

import lombok.Data;

@Data
public class Player {
    String name;
    int currPos = -1;

    public Player(String name) {
        this.name = name;
    }
}
