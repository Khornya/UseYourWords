package com.github.khornya.useyourwords.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.github.khornya.useyourwords.model.Element;
import com.github.khornya.useyourwords.model.ElementType;
import com.github.khornya.useyourwords.service.ElementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/element")
public class ElementController {

    @Autowired
    private ElementService srvElement;

    @Autowired
    private Cloudinary cloudinary;

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
