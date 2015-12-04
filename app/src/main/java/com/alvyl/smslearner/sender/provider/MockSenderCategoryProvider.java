package com.alvyl.smslearner.sender.provider;

import com.alvyl.smslearner.message.model.Category;
import com.alvyl.smslearner.message.model.CategoryType;
import com.alvyl.smslearner.message.model.Template;
import com.alvyl.smslearner.sender.model.Sender;
import com.alvyl.smslearner.sender.model.SenderCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dostiharise
 */
public class MockSenderCategoryProvider {

    private static final String NON_EMPTY_STRING_REGEX_EXPR = "(.+)";

    private static MockSenderCategoryProvider selfInstance;

    final Map<Sender, List<SenderCategory>> senderCategoriesMap = new HashMap<>();

    // marked private to discourage instantiation.
    private MockSenderCategoryProvider() {
        initSenderCategories();
    }

    public static MockSenderCategoryProvider getInstance() {

        if(null == selfInstance) {
            selfInstance = new MockSenderCategoryProvider();
        }

        return selfInstance;
    }

    public List<SenderCategory> findBySender(final Sender sender) {
        return  senderCategoriesMap.get(sender);
    }

    private void initSenderCategories() {

        final MockSenderProvider senderProvider = MockSenderProvider.getInstance();

        {

            final List<SenderCategory> senderCategoriesBySender = new ArrayList<>();

            final Sender sender = senderProvider.findSender(MockSenderProvider.OLA_CABS_SENDER_ADDRESS);

            senderCategoriesBySender.add(buildSenderCategory(sender, "Travel", "Booking Confirmed",
                    "Say Ola to your driver(.+)for(.+)\\.(.+)to pick you up @(.+)\\.(.+)"));

            senderCategoriesBySender.add(buildSenderCategory(sender, "Travel", "Booking Cancelled",
                    "Ola! Your booking(.+) has been cancelled as per your request\\.(.+)"));

            senderCategoriesMap.put(sender, senderCategoriesBySender);
        }

        {
            final List<SenderCategory> senderCategoriesBySender = new ArrayList<>();

            final Sender sender = senderProvider.findSender(MockSenderProvider.HDFC_BANK_SENDER_ADDRESS);

            senderCategoriesBySender.add(buildSenderCategory(sender, "Banking", "Cash Withdrawal",
                    "(.+) was withdrawn using your HDFC Bank Card ending (.+) on (.+) at (.+)"));

            senderCategoriesBySender.add(buildSenderCategory(sender, "Banking", "Debit Transaction",
                    "Thank you for using Debit Card ending (.+) for (.+) in (.+) at (.+) on (.+)"));

            senderCategoriesMap.put(sender, senderCategoriesBySender);
        }


    }

    private SenderCategory buildSenderCategory(final Sender sender, final String categoryTypeName,
            final String categoryName, final String templateString) {

        final SenderCategory senderCategory = new SenderCategory();

        senderCategory.setSender(sender);
        senderCategory.setCategory(new Category(categoryName, new CategoryType(categoryTypeName)));
        senderCategory.setTemplate(new Template(templateString));

        return senderCategory;

    }

}
