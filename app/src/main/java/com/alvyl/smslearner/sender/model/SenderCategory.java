package com.alvyl.smslearner.sender.model;


import com.alvyl.smslearner.message.model.Category;
import com.alvyl.smslearner.message.model.Template;

/**
 * @author dostiharise
 */
public class SenderCategory {

    private Long id;

    /**
     * The sender this category belongs to.
     */
    private Sender sender;

    /**
     * The category definition.
     */
    private Category category;

    /**
     * The template used to match and categorize {@link com.alvyl.smslearner.message.model.Message}.
     */
    private Template template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "SenderCategory{" +
                "id=" + id +
                ", sender=" + sender +
                ", category=" + category +
                ", template=" + template +
                '}';
    }
}
