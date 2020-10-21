package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.dao.IScoreRepository;
import com.github.khornya.useyourwords.exceptions.ScoreNotFoundException;
import com.github.khornya.useyourwords.models.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private IScoreRepository daoScore;

    public List<Score> findAll() {
        return this.daoScore.findAll();
    }

    public Score findById(int id) {
        return this.daoScore.findById(id).orElseThrow(ScoreNotFoundException::new);
    }

    public Score save(Score score) {
        return this.daoScore.save(score);
    }

    public void add(Score score) {
        this.daoScore.save(score);
    }

    public void deleteById(int id) {
        this.daoScore.deleteById(id);
    }

}
