package com.github.khornya.useyourwords.controller;

import com.github.khornya.useyourwords.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scoreboard")
public class ScoreBoardController {

    @Autowired
    private ScoreService srvScore;

    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("scores", this.srvScore.findAll());

        return "scoreBoard/scoreBoard";
    }

}
