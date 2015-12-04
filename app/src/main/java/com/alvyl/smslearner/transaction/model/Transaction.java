package com.alvyl.smslearner.transaction.model;

import com.alvyl.smslearner.message.model.Message;
import com.alvyl.smslearner.sender.model.SenderCategory;

import java.util.HashMap;

/**
 * @author dostiharise
 */
public class Transaction {

    private Long id;

    /**
     * The message from which this transaction has been computed
     */
    private Message message;

    /**
     * The computed classification for this transaction.
     */
    private SenderCategory senderCategory;

    /**
     * The transaction data computed from the {@link Message}.
     */
    private HashMap<String, String> data = new HashMap<>();
}
