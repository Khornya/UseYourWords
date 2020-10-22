package com.github.khornya.useyourwords.dao;

import com.github.khornya.useyourwords.model.Element;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IElementRepository extends JpaRepository<Element, Integer> {

}
