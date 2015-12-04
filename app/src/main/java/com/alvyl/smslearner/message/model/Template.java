package com.alvyl.smslearner.message.model;

/**
 * @author dostiharise
 */
public class Template {

    private Long id;

    private String pattern;

    public Template() {
    }

    public Template(String pattern) {
        this.pattern = pattern;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", pattern='" + pattern + '\'' +
                '}';
    }
}
