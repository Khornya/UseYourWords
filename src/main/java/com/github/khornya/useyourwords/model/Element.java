package com.github.khornya.useyourwords.model;

import javax.persistence.*;

@Entity // ANNOTATION OBLIGATOIRE POUR UNE ENTITE
@Table(name = "ELEMENT")
public class Element {

    @Id // ANNOTATION OBLIGATOIRE - ID NECESSAIRE
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "ELEMENT_ID")
    private int id;

    @Column(name = "ELEMENT_NAME", length = 200)
    private String name;

    @Column(name = "ELEMENT_URL", length = 500)
    private String url;

    @Column(name = "ELEMENT_TYPE", length = 10)
    @Enumerated(EnumType.ORDINAL)
    private ElementType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
