package com.github.khornya.useyourwords.model;

public class GameCreationMessage {
    private String name;
    private int numOfPlayers;
    private int numOfTeams;
    private int numOfRounds;

    public GameCreationMessage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public int getNumOfTeams() {
        return numOfTeams;
    }

    public void setNumOfTeams(int numOfTeams) {
        this.numOfTeams = numOfTeams;
    }

    public int getNumOfRounds() {
        return numOfRounds;
    }

    public void setNumOfRounds(int numOfRounds) {
        this.numOfRounds = numOfRounds;
    }
}
