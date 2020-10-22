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

        String dossier = "";

        if (element.getType() == ElementType.PHOTO) {
            dossier = "Photos";
        } else if (element.getType() == ElementType.VIDEO) {
            dossier = "Vidéos";
        }

        if (element.getUuid() == null) {
            element.setUuid(UUID.randomUUID().toString().replace("-", ""));
        }

        if (element.getType() != ElementType.TEXT) {
            Map params = ObjectUtils.asMap(
                    "public_id", dossier + "/" + element.getName() + "_" + element.getUuid(),
                    "overwrite", true,
                    "notification_url", "https://mysite/notify_endpoint",
                    "resource_type", "auto"
            );

            File f = Files.createTempFile("temp", file.getOriginalFilename()).toFile();
            file.transferTo(f);
            Map uploadResult = cloudinary.uploader().upload(f, params);
            element.setUrl(uploadResult.get("secure_url").toString());
        }

        this.srvElement.add(element);

        return "redirect:./liste";
    }

    @GetMapping("/supprimer/{id}")
    public String deleteById(@PathVariable int id) throws IOException {
        Element element = this.srvElement.findById(id);

        if (element.getType() == ElementType.PHOTO) {
            cloudinary.uploader().destroy("Photos/" + element.getName() + "_" + element.getUuid(), ObjectUtils.emptyMap());
        } else if (element.getType() == ElementType.VIDEO) {
            cloudinary.uploader().destroy("Vidéos/" + element.getName() + "_" + element.getUuid(), ObjectUtils.asMap("resourceType", "video"));
        }

        this.srvElement.deleteById(id);

        return "redirect:../liste";
    }

    @GetMapping("/editer")
    public String update(@RequestParam int id, Model model) throws IOException {
        Element elementToEdit = new Element() ;
        elementToEdit.setName(this.srvElement.findById(id).getName());
        elementToEdit.setDefaultResponse(this.srvElement.findById(id).getDefaultResponse());
        if(this.srvElement.findById(id).getType() == ElementType.TEXT)
        {
            elementToEdit.setToFillText(this.srvElement.findById(id).getToFillText());
        }

        model.addAttribute("element", elementToEdit);
        deleteById(id);

        return "element/form";
    }

    @PostMapping("/editer")
    public String update(@RequestParam int id, Element element) {
        this.srvElement.save(element);

        return "redirect:./liste";
    }
}
