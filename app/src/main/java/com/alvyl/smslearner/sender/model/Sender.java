package com.alvyl.smslearner.sender.model;

/**
 * @author dostiharise
 */
public class Sender {

    private Long id;

    private String name;

    /**
     * A 6 character alphabetic code, for sending transactions messages
     */
    private SenderAddress senderAddress;

    public Sender() {
    }

    public Sender(String name, SenderAddress senderAddress) {
        this.name = name;
        this.senderAddress = senderAddress;
    }

    public Sender(Long id, String name, SenderAddress senderAddress) {
        this.id = id;
        this.name = name;
        this.senderAddress = senderAddress;
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

    public SenderAddress getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(SenderAddress senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", senderAddress=" + senderAddress +
                '}';
    }
}
