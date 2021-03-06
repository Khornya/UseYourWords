package com.github.khornya.useyourwords.model;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "SCORE_DATE")
    private LocalDateTime date;

    public Score() {
    }

    public Score(String playerName, int score, LocalDateTime date) {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
