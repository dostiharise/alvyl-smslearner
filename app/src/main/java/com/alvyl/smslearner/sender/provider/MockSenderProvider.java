package com.alvyl.smslearner.sender.provider;

import com.alvyl.smslearner.sender.model.Sender;
import com.alvyl.smslearner.sender.model.SenderAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dostiharise
 */
public class MockSenderProvider {

    public static final String OLA_CABS_READABLE_NAME = "Ola Cabs";
    public static final String OLA_CABS_SENDER_ADDRESS = "OLACAB";

    public static final String HDFC_BANK_READABLE_NAME = "Hdfc Bank";
    public static final String HDFC_BANK_SENDER_ADDRESS = "HDFCBK";

    private static MockSenderProvider selfInstance;

    private Map<String, Sender> sendersByAddress = new HashMap<>();

    // marked private to discourage instantiation.
    private MockSenderProvider() {
        initSenders();
    }

    public static MockSenderProvider getInstance() {

        if(null == selfInstance) {
            selfInstance = new MockSenderProvider();
        }

        return selfInstance;
    }

    public void initSenders() {

        registerSender(new Sender(OLA_CABS_READABLE_NAME,
                SenderAddress.toSenderAddress(SenderAddress.Type.TRANSACTIONAL_ADDRESS,
                        OLA_CABS_SENDER_ADDRESS)));

        registerSender(new Sender(HDFC_BANK_READABLE_NAME,
                SenderAddress.toSenderAddress(SenderAddress.Type.TRANSACTIONAL_ADDRESS,
                        HDFC_BANK_SENDER_ADDRESS)));


    }

    private void registerSender(final Sender sender) {
        this.sendersByAddress.put(sender.getSenderAddress().getValue(), sender);
    }

    // FIXME: Perform database lookup, instead of in memory HashMap lookup
    public Sender findSender(final String senderAddress) {
        return sendersByAddress.get(senderAddress);
    }

    public List<Sender> findAllSenders() {

        final List<Sender> senders = new ArrayList<>();

        senders.addAll(sendersByAddress.values());

        return senders;
    }
}
