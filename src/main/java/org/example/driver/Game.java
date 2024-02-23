package org.example.driver;

import org.example.model.Cell;
import org.example.model.Jump;
import org.example.model.Player;

import java.util.*;


// note for simplicity board is like this
//  0   1   2   3    4   5   6    7    8   9
// 19   18  17   16  15 14   13   12   11 10
// .
// .
// 90  91  92   93   94 95   96  97   98  99

// 99 is the winner

public class Game {
    private Cell[] cells;
    private Deque<Player> players = new ArrayDeque<>();
    private int size;
    private int dices; // number of dices to roll;
    Player winner; // who won;

    public boolean addPlayer(Player player) {
        return players.add(player);
    }


    public void initialize(int size, int snakes, int ladders, int dices) {
        this.size = size;
        cells = new Cell[size * size];
        for(int i = 0;i<size*size;i++) {
            cells[i] = Cell.builder().build();
        }
        addSnakes(snakes);
        addLadders(ladders);
        this.dices = dices;
    }

    public Player startGame() {
        Player winner = null;
        while (winner == null) {
            Player player = players.poll();
            int currPos = player.getCurrPos();
            if(currPos == (size*size-1)) {
                System.out.println("won by player" + player);
                winner = player;
                return winner;
            }
            int rolledDice = getDiceRoll();
            System.out.println("Player "+ player.getName() +" rolled dice to "+ rolledDice);
            int moveTo = currPos + rolledDice;
            if(moveTo > size*size-1) {
                System.out.println("oops cant move");
                players.addLast(player);
                continue;
            } else {
                System.out.println(" moved to " + moveTo);
                player.setCurrPos(moveTo);
            }
            if (cells[moveTo].getJump() != null) { // either snake of ladder

                Jump j = cells[moveTo].getJump();
                moveTo = j.getTo();
                player.setCurrPos(moveTo);

                if (j.getFrom() < j.getTo()) {// ladder
                    players.addFirst(player); // chance again
                    System.out.println("Yeah Its ladder , " + players.getFirst() + " movedto "+ moveTo);

                    System.out.println("player " + player.getName() + " gets another chance");
                    continue;
                } else {
                    System.out.println("oops bit by snake = " + players.getFirst() + " movedto "+ moveTo);
                }
            }

            printBoard();
            players.addLast(player); // else add to last
        }
        return null;
    }

    private int getDiceRoll() {
        int rolltimes = this.dices;
        int v = 0;
        while (rolltimes-- > 0) {
            v += (int)(Math.random() * 6 + 1);
        }
        return v;
    }

    private void addSnakes(int snakes) {
        int maxV = size * size;
        while (snakes > 0) {
            int st = (int) (Math.random() * maxV); // 0 to 99 in case of size 10
            int en = (int) (Math.random() * maxV);
            if (st > en && cells[st].getJump() == null && cells[en].getJump() == null) {
                cells[st].setJump(Jump.builder().from(st).to(en).build());
                System.out.println("snake is at " + st + " to " + en);
                snakes--;
            }
        }
    }

    private void addLadders(int ladders) {
        int maxV = size * size - 1;
        while (ladders > 0) {
            int st = (int) (Math.random() * (maxV) - 1); // 0 to 99 in case of size 10
            int en = (int) (Math.random() * (maxV) - 1);
            if (st < en && cells[st].getJump() == null && cells[en].getJump() == null) {
                cells[st].setJump(Jump.builder().from(st).to(en).build());
                System.out.println("Ladder from " + st + " to " + en);
                ladders--;
            }
        }
    }


    private void printBoard() {
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < size; j++) {
                    int cellN = (i * size) + (j); // cell[cellN-1] is at pos cellN
                    if(cells[cellN].getJump() != null) {
                        System.out.print(cellN + "**       "); // special
                    } else {
                        System.out.print(cellN + "         ");
                    }
                }
            } else {
                for (int j = size - 1; j >= 0; j--) {
                    int cellN = (i * size) + (j);
                    if(cells[cellN].getJump() != null) {
                        System.out.print(cellN + "**       "); // special
                    } else {
                        System.out.print(cellN + "         ");
                    }
                }
            }
            System.out.println();
        }
    }

}
