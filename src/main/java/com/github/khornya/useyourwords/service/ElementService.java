package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.dao.IElementRepository;
import com.github.khornya.useyourwords.exceptions.ElementNotFoundException;
import com.github.khornya.useyourwords.model.Element;
import com.github.khornya.useyourwords.model.ElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementService {
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

    public Element save(Element element) {
        return this.daoElement.save(element);
    }

    public void add(Element element) {
        this.daoElement.save(element);
    }

    public void deleteById(int id) {
        this.daoElement.deleteById(id);
    }
}
