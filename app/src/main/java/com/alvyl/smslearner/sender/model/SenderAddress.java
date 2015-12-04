package com.alvyl.smslearner.sender.model;

/**
 * @author dostiharise
 */
public class SenderAddress {

    public enum Type {
        TRANSACTIONAL_ADDRESS,
        PROMOTIONAL_ADDRESS
    }

    private Type type;

    private String value;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static SenderAddress toSenderAddress(final Type type, final String address) {
        final SenderAddress senderAddress = new SenderAddress();

        senderAddress.setType(type);
        senderAddress.setValue(address);

        return senderAddress;
    }

    @Override
    public String toString() {
        return "SenderAddress{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
