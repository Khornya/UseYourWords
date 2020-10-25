package com.github.khornya.useyourwords.controller;

import com.cloudinary.Cloudinary;
import com.github.khornya.useyourwords.model.Element;
import com.github.khornya.useyourwords.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/element")
public class ElementController {

    @Autowired
    private ElementService srvElement;

    @GetMapping("/home")
    public String home() {
        return "element/home";
    }

    @GetMapping("/liste")
    public String findAll(Model model) {
        model.addAttribute("elements", this.srvElement.findAll());

        return "element/list";
    }

    @GetMapping("/ajouter")
    public String add() {
        return "element/form";
    }

    @PostMapping("/ajouter")
    public String add(Element element, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        this.srvElement.add(element, file);
        return "redirect:./liste";
    }

    @GetMapping("/supprimer/{id}")
    public String deleteById(@PathVariable int id) throws IOException {
        this.srvElement.deleteById(id);
        return "redirect:../liste";
    }

    @GetMapping("/editer")
    public String update(@RequestParam int id, Model model) {
        model.addAttribute("element", this.srvElement.findById(id));
        return "element/form";
    }

    @PostMapping("/editer/{id}")
    public String update(Element element, @PathVariable int id, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        this.srvElement.update(element, id, file);
        return "redirect:../liste";
    }
}
