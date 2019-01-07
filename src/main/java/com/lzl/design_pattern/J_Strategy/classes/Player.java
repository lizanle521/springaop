package com.lzl.design_pattern.J_Strategy.classes;

import com.lzl.design_pattern.J_Strategy.Interfaces.Strategy;

public class Player
{
    private String name;
    private Strategy strategy;

    private int loseCount;
    private int winCount;
    private int gameCount;

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public void win(){
        strategy.stuty(true);
        winCount++;
        gameCount++;
    }

    public void lose(){
        strategy.stuty(false);
        loseCount++;
        gameCount++;
    }

    public void even(){
        gameCount++;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", strategy=" + strategy +
                ", loseCount=" + loseCount +
                ", winCount=" + winCount +
                ", gameCount=" + gameCount +
                '}';
    }
}
