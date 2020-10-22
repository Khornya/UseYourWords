package com.github.khornya.useyourwords.dao;

import com.github.khornya.useyourwords.model.Element;
import com.github.khornya.useyourwords.model.ElementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IElementRepository extends JpaRepository<Element, Integer> {
    @Query("SELECT e FROM Element e where e.type = ?1 order by function('RAND')")
    List<Element> findRandomElementsByType(ElementType type);
}
