package com.github.khornya.useyourwords.controller;

import com.github.khornya.useyourwords.model.Element;
import com.github.khornya.useyourwords.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/element")
public class ElementRestController {
    @Autowired
    private ElementService elementService;

    @GetMapping("")
    public List<Element> findAll() {
        return this.elementService.findAll();
    }

    @PostMapping("")
    public Element add(@RequestBody Element element, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        this.elementService.add(element, file);
        return element;
    }

    @PutMapping("/{id}")
    public Element edit(@RequestBody Element element, @PathVariable int id, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        this.elementService.update(element, id, file);
        return element;
    }

    @PatchMapping("/{id}")
    public Element partialEdit(@RequestBody Map<String, Object> fields, @PathVariable int id, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Element element = this.elementService.findById(id);
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Element.class, key);
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, element, value);
        });
        this.elementService.update(element, id, file);
        return element;
    }
}
