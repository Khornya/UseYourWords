package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.dao.IScoreRepository;
import com.github.khornya.useyourwords.exceptions.ScoreNotFoundException;
import com.github.khornya.useyourwords.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private IScoreRepository daoScore;

    public List<Score> findAllOrderByScoreDescending() {
        return this.daoScore.findAll(Sort.by(Sort.Direction.DESC, "score"));
    }

    public Score findById(int id) {
        return this.daoScore.findById(id).orElseThrow(ScoreNotFoundException::new);
    }

    public Score add(Score score) {
        return this.daoScore.save(score);
    }

    public void deleteById(int id) {
        this.daoScore.deleteById(id);
    }

}
