package com.github.khornya.useyourwords.dao;

import com.github.khornya.useyourwords.models.Element;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IElementRepository extends JpaRepository<Element, Integer> {
    Element findByIdAndName(int id, String name);
}
