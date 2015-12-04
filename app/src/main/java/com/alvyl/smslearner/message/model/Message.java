package com.alvyl.smslearner.message.model;

import com.alvyl.smslearner.sender.model.Sender;
import com.alvyl.smslearner.sender.model.SenderCategory;

import java.util.Date;

/**
 * @author dostiharise
 */
public class Message {

    private Long id;


    /**
     * An 'externalId' to track this message in an external system.<br/>
     * Typically an 'id' imported into the app, from the source.
     */
    private String externalId;

    private String fromAddress;
    private String content;

    private Date receivedDate;
    private Date sentDate;

    // TODO: The following fields are computed, hence can be refactored into a separate model object

    /**
     * The computed sender this message has been received from.
     */
    private Sender sender;

    /**
     * The computer category for the above identified sender.
     */
    private SenderCategory senderCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public SenderCategory getSenderCategory() {
        return senderCategory;
    }

    public void setSenderCategory(SenderCategory senderCategory) {
        this.senderCategory = senderCategory;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", content='" + content + '\'' +
                ", receivedDate=" + receivedDate +
                ", sentDate=" + sentDate +
                ", sender=" + sender +
                ", senderCategory=" + senderCategory +
                '}';
    }
}
