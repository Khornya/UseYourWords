package com.github.khornya.useyourwords.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.github.khornya.useyourwords.dao.IElementRepository;
import com.github.khornya.useyourwords.exception.ElementNotFoundException;
import com.github.khornya.useyourwords.model.Element;
import com.github.khornya.useyourwords.model.ElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ElementService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private IElementRepository daoElement;

    public List<Element> findAll() {
        return this.daoElement.findAll();
    }

    public Element findById(int id) {
        return this.daoElement.findById(id).orElseThrow(ElementNotFoundException::new);
    }

    public Element findRandomElementByType(ElementType type) {
        List<Element> list = this.daoElement.findRandomElementsByType(type);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public Element update(Element element, int id, MultipartFile file) throws IOException {
        Element elementToEdit = findById(id);
        element.setId(elementToEdit.getId());
        element.setUuid(elementToEdit.getUuid());
        if (!file.isEmpty() && element.getType() != ElementType.TEXT) {
            String dossier = "";
            if (element.getType() == ElementType.PHOTO) {
                dossier = "Photos";
            } else if (element.getType() == ElementType.VIDEO) {
                dossier = "Vidéos";
            }
            uploadToCloudinary(element, file, dossier);
        } else if (element.getType() != ElementType.TEXT) {
            element.setUrl(elementToEdit.getUrl());
        }
        return this.daoElement.save(element);
    }

    public void add(Element element, MultipartFile file) throws IOException {
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
            uploadToCloudinary(element, file, dossier);
        }
        this.daoElement.save(element);
    }

    private void uploadToCloudinary(Element element, MultipartFile file, String dossier) throws IOException {
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

    public void deleteById(int id) throws IOException {
        Element element = findById(id);
        if (element.getType() == ElementType.PHOTO) {
            cloudinary.uploader().destroy("Photos/" + element.getName() + "_" + element.getUuid(), ObjectUtils.emptyMap());
        } else if (element.getType() == ElementType.VIDEO) {
            cloudinary.uploader().destroy("Vidéos/" + element.getName() + "_" + element.getUuid(), ObjectUtils.asMap("resourceType", "video"));
        }
        this.daoElement.deleteById(id);
    }
}
