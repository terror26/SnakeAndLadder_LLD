package org.example;

import org.example.driver.Game;
import org.example.model.Player;

import java.sql.Driver;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        new Main().start();
    }

    private void start() {
        Game game = new Game();
        game.addPlayer(new Player("Kanishk"));
        game.addPlayer(new Player("Manish"));
        game.addPlayer(new Player("Rahul"));
        game.initialize(10,3,3,1);
        game.startGame();
    }
}