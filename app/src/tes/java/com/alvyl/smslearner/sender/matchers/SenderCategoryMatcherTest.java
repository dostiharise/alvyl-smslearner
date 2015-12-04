package com.alvyl.smslearner.sender.matchers;

import com.alvyl.smslearner.message.model.Template;
import com.alvyl.smslearner.sender.model.SenderCategory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dostiharise
 */
public class SenderCategoryMatcherTest {

    @Test
    public void testMatchesBookingConfirmed() throws Exception {

        final Template template = new Template();
        template.setPattern("Say Ola to your driver(.+)for(.+)\\.(.+)to pick you up @(.+)\\.(.+)");

        final SenderCategory senderCategory = new SenderCategory();
        senderCategory.setTemplate(template);

        final SenderCategoryMatcher matcher = new SenderCategoryMatcher(senderCategory);

        assertTrue(matcher.matches("Say Ola to your driver DRIVER X  (123445566) for CRN12345678. " +
                "White Indica XX 01 X 1234  to pick you up @ 8:46 PM, 29Nov. " +
                "1.8 times peak time charge is applicable on this booking. " +
                "Please share https://www.olacabs.com/track/xxxxxxxxxx with friends/family and let them track you."));

        // FIXME: Split this below assertions to separate test cases
        assertFalse(matcher.matches(""));
        assertFalse(matcher.matches("Say Ola to your driver."));

        assertFalse(matcher.matches("Say Ola to your driver DRIVER X  (123445566) for CRN12345678."));

        assertFalse(matcher.matches("Say Ola to your driver  for ." +
                "  to pick you up @ ."));

    }

    @Test
    public void testMatchesBookingCancelled() throws Exception {

        final Template template = new Template();
        template.setPattern("Ola! Your booking (.+) has been cancelled as per your request\\.(.+)");

        final SenderCategory senderCategory = new SenderCategory();
        senderCategory.setTemplate(template);

        final SenderCategoryMatcher matcher = new SenderCategoryMatcher(senderCategory);

        assertTrue(matcher.matches("Ola! Your booking CNR123456 has been cancelled as per your request. " +
                "For queries, call 080-12341234"));

    }
}