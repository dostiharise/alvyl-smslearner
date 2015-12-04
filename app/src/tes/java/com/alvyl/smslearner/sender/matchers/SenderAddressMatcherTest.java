package com.alvyl.smslearner.sender.matchers;

import com.alvyl.smslearner.sender.model.SenderAddress;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dostiharise
 */
public class SenderAddressMatcherTest {

    @Test
    public void testMatches() throws Exception {

        final SenderAddressMatcher matcher = new SenderAddressMatcher(
                SenderAddress.toSenderAddress(SenderAddress.Type.TRANSACTIONAL_ADDRESS, "OLACAB"));

        assertTrue(matcher.matches("VK-OLACAB"));

        // FIXME: Split this below assertions to separate test cases
        assertFalse(matcher.matches("VK-OLACABS"));
        assertFalse(matcher.matches("OLACAB"));
        assertFalse(matcher.matches("OLACAB-XK"));

    }
}