package com.alvyl.smslearner.sender.matchers;

import com.alvyl.smslearner.sender.model.SenderAddress;

import java.util.regex.Pattern;

/**
 * @author dostiharise
 */
public class SenderAddressMatcher {

    /**
     * 6 character sender id pattern, for transactional SMSes.<br/>
     * Example: <code>VK-OLACAB</code>
     */
    private static final String TRANSACTIONAL_PREFIX = "..-";

    private SenderAddress senderAddress;

    private Pattern pattern;

    public SenderAddressMatcher(final SenderAddress senderAddress) {
        this.senderAddress = senderAddress;

        //TODO: Validate sender address

        if(this.senderAddress.getType() == SenderAddress.Type.TRANSACTIONAL_ADDRESS) {
            pattern = Pattern.compile(TRANSACTIONAL_PREFIX+senderAddress.getValue());
        }
    }

    public boolean matches(final String address) {

        if(null == address) {
            return false;
        }

        return pattern.matcher(address.trim()).matches();
    }

}
