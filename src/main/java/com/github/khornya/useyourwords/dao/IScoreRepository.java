package com.github.khornya.useyourwords.dao;

import com.github.khornya.useyourwords.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IScoreRepository extends JpaRepository<Score, Integer> {

}
