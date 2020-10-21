package com.github.khornya.useyourwords.models;

import javax.persistence.*;
import java.util.Date;

@Entity // ANNOTATION OBLIGATOIRE POUR UNE ENTITE
@Table(name = "SCORE")
public class Score {

    @Id // ANNOTATION OBLIGATOIRE - ID NECESSAIRE
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "SCORE_ID")
    private int id;

    @Column(name = "SCORE_NAME", length = 50)
    private String playerName;

    @Column(name = "SCORE_SCORE")
    private int score;

    @Column(name = "SCORE_DATELASTGAME")
    private Date lastGame;

    @Column(name = "ELEMENT_TOTALGAME")
    private int totalGame;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getLastGame() {
        return lastGame;
    }

    public void setLastGame(Date lastGame) {
        this.lastGame = lastGame;
    }

    public int getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(int totalGame) {
        this.totalGame = totalGame;
    }
}