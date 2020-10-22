package com.github.khornya.useyourwords.models;

import javax.persistence.*;

@Entity
@Table(name = "ELEMENT")
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELEMENT_ID")
    private int id;

    @Column(name = "ELEMENT_NAME", length = 200)
    private String name;

    @Column(name = "ELEMENT_TOFILLTEXT", length = 500)
    private String toFillText;

    @Column(name = "ELEMENT_URL", length = 500)
    private String url;

    @Column(name = "ELEMENT_DEFAULTRESPONSE", length = 500)
    private String defaultResponse;

    @Column(name = "ELEMENT_TYPE", length = 10)
    @Enumerated(EnumType.ORDINAL)
    private ElementType type;

    @Column(name = "ELEMENT_UUID", length = 500)
    private String uuid;

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

    public String getToFillText() {
        return toFillText;
    }

    public void setToFillText(String toFillText) {
        this.toFillText = toFillText;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDefaultResponse() {
        return defaultResponse;
    }

    public void setDefaultResponse(String defaultResponse) {
        this.defaultResponse = defaultResponse;
    }
}
