package com.alvyl.smslearner.message.model;

/**
 * @author dostiharise
 */
public class CategoryType {

    private Long id;

    private String name;

    public CategoryType() {
    }

    public CategoryType(String name) {
        this.name = name;
    }

    public CategoryType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
